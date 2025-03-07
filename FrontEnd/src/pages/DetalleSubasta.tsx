import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import {
    Container,
    Grid,
    Card,
    CardContent,
    Typography,
    Button,
    TextField,
    Box,
    Alert,
    Paper,
    List,
    ListItem,
    ListItemText,
    Divider
} from '@mui/material';
import { subastaService, pujaService } from '../services/api';
import { Subasta, Auto, Puja } from '../types/types';
import { useAuth } from '../context/AuthContext';
import io from 'socket.io-client';

const DetalleSubasta: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [subasta, setSubasta] = useState<Subasta | null>(null);
    const [pujas, setPujas] = useState<Record<number, Puja[]>>({});
    const [montos, setMontos] = useState<Record<number, string>>({});
    const [error, setError] = useState<string>('');
    const { user } = useAuth();

    useEffect(() => {
        const socket = io('http://localhost:8080'); // Ajusta según tu configuración

        socket.on('connect', () => {
            console.log('Conectado al servidor de WebSocket');
            if (id) {
                socket.emit('join-subasta', id);
            }
        });

        socket.on('nueva-puja', (nuevaPuja: Puja) => {
            setPujas(prev => ({
                ...prev,
                [nuevaPuja.autoId]: [...(prev[nuevaPuja.autoId] || []), nuevaPuja]
            }));
        });

        const cargarSubasta = async () => {
            try {
                if (id) {
                    const data = await subastaService.getSubasta(parseInt(id));
                    setSubasta(data);

                    // Cargar pujas iniciales para cada auto
                    const pujasPromises = data.autos.map(auto =>
                        pujaService.getPujasPorSubasta(data.id)
                    );
                    const pujasResults = await Promise.all(pujasPromises);
                    const pujasMap: Record<number, Puja[]> = {};
                    data.autos.forEach((auto, index) => {
                        pujasMap[auto.id] = pujasResults[index];
                    });
                    setPujas(pujasMap);
                }
            } catch (error) {
                console.error('Error al cargar la subasta:', error);
                setError('Error al cargar la subasta');
            }
        };

        cargarSubasta();

        return () => {
            socket.disconnect();
        };
    }, [id]);

    const handlePuja = async (auto: Auto) => {
        try {
            const monto = parseFloat(montos[auto.id] || '0');
            if (monto <= 0) {
                setError('El monto debe ser mayor a 0');
                return;
            }

            const ultimaPuja = pujas[auto.id]?.[pujas[auto.id].length - 1];
            if (ultimaPuja && monto <= ultimaPuja.monto) {
                setError('El monto debe ser mayor a la última puja');
                return;
            }

            if (user) {
                await pujaService.crearPuja({
                    subastaId: subasta?.id || 0,
                    autoId: auto.id,
                    compradorId: user.id,
                    monto: monto,
                    fecha: new Date().toISOString()
                });

                setMontos(prev => ({ ...prev, [auto.id]: '' }));
                setError('');
            }
        } catch (error) {
            console.error('Error al realizar la puja:', error);
            setError('Error al realizar la puja');
        }
    };

    if (!subasta) {
        return (
            <Container>
                <Typography>Cargando...</Typography>
            </Container>
        );
    }

    return (
        <Container maxWidth="lg" sx={{ mt: 4 }}>
            <Typography variant="h4" gutterBottom>
                Subasta #{subasta.id}
            </Typography>
            <Typography color="text.secondary" gutterBottom>
                Inicio: {new Date(subasta.fechaInicio).toLocaleString()}
            </Typography>
            <Typography color="text.secondary" gutterBottom>
                Fin: {new Date(subasta.fechaFin).toLocaleString()}
            </Typography>

            {error && (
                <Alert severity="error" sx={{ mt: 2, mb: 2 }}>
                    {error}
                </Alert>
            )}

            <Grid container spacing={3} sx={{ mt: 2 }}>
                {subasta.autos.map((auto) => (
                    <Grid item xs={12} md={6} key={auto.id}>
                        <Card>
                            <CardContent>
                                <Typography variant="h6">
                                    {auto.marca} {auto.modelo} ({auto.año})
                                </Typography>
                                <Typography color="text.secondary">
                                    Precio Base: ${auto.precioBase}
                                </Typography>
                                <Typography variant="body2" sx={{ mt: 2 }}>
                                    {auto.descripcion}
                                </Typography>

                                <Paper sx={{ mt: 2, mb: 2, p: 2 }}>
                                    <Typography variant="subtitle2" gutterBottom>
                                        Historial de Pujas
                                    </Typography>
                                    <List>
                                        {(pujas[auto.id] || []).map((puja, index) => (
                                            <React.Fragment key={puja.id}>
                                                <ListItem>
                                                    <ListItemText
                                                        primary={`$${puja.monto}`}
                                                        secondary={new Date(puja.fecha).toLocaleString()}
                                                    />
                                                </ListItem>
                                                {index < (pujas[auto.id] || []).length - 1 && <Divider />}
                                            </React.Fragment>
                                        ))}
                                    </List>
                                </Paper>

                                {user?.role === 'COMPRADOR' && subasta.estado === 'ACTIVA' && (
                                    <Box sx={{ mt: 2 }}>
                                        <TextField
                                            fullWidth
                                            label="Monto de la puja"
                                            type="number"
                                            value={montos[auto.id] || ''}
                                            onChange={(e) =>
                                                setMontos(prev => ({
                                                    ...prev,
                                                    [auto.id]: e.target.value
                                                }))
                                            }
                                            sx={{ mb: 1 }}
                                        />
                                        <Button
                                            variant="contained"
                                            fullWidth
                                            onClick={() => handlePuja(auto)}
                                        >
                                            Realizar Puja
                                        </Button>
                                    </Box>
                                )}
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
};

export default DetalleSubasta; 