import React, { useEffect, useState } from 'react';
import {
    Container,
    Grid,
    Card,
    CardContent,
    CardMedia,
    Typography,
    Button,
    Box,
    Chip
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { subastaService } from '../services/api';
import { Subasta } from '../types/types';
import { useAuth } from '../context/AuthContext';

const SubastasPage: React.FC = () => {
    const [subastas, setSubastas] = useState<Subasta[]>([]);
    const { user } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSubastas = async () => {
            try {
                const data = await subastaService.getSubastasActivas();
                setSubastas(data);
            } catch (error) {
                console.error('Error al cargar subastas:', error);
            }
        };

        fetchSubastas();
    }, []);

    const getEstadoColor = (estado: string) => {
        switch (estado) {
            case 'ACTIVA':
                return 'success';
            case 'PENDIENTE':
                return 'warning';
            case 'FINALIZADA':
                return 'error';
            default:
                return 'default';
        }
    };

    return (
        <Container maxWidth="lg" sx={{ mt: 4 }}>
            <Box display="flex" justifyContent="space-between" alignItems="center" mb={4}>
                <Typography variant="h4" component="h1">
                    Subastas Disponibles
                </Typography>
                {user?.role === 'VENDEDOR' && (
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={() => navigate('/subastas/nueva')}
                    >
                        Crear Nueva Subasta
                    </Button>
                )}
            </Box>

            <Grid container spacing={3}>
                {subastas.map((subasta) => (
                    <Grid item xs={12} sm={6} md={4} key={subasta.id}>
                        <Card>
                            <CardContent>
                                <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                                    <Typography variant="h6" component="div">
                                        Subasta #{subasta.id}
                                    </Typography>
                                    <Chip
                                        label={subasta.estado}
                                        color={getEstadoColor(subasta.estado) as any}
                                        size="small"
                                    />
                                </Box>
                                <Typography color="text.secondary" gutterBottom>
                                    Inicio: {new Date(subasta.fechaInicio).toLocaleString()}
                                </Typography>
                                <Typography color="text.secondary" gutterBottom>
                                    Fin: {new Date(subasta.fechaFin).toLocaleString()}
                                </Typography>
                                <Typography variant="body2" color="text.secondary" mt={2}>
                                    Autos en subasta: {subasta.autos.length}
                                </Typography>
                                <Button
                                    variant="outlined"
                                    fullWidth
                                    sx={{ mt: 2 }}
                                    onClick={() => navigate(`/subastas/${subasta.id}`)}
                                >
                                    Ver Detalles
                                </Button>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
};

export default SubastasPage; 