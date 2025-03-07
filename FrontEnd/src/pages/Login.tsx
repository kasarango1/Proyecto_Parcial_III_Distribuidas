import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Formik, Form } from 'formik';
import * as Yup from 'yup';
import {
    Container,
    Paper,
    Typography,
    TextField,
    Button,
    Box,
    Alert
} from '@mui/material';
import { useAuth } from '../context/AuthContext';
import { authService } from '../services/api';

const validationSchema = Yup.object({
    username: Yup.string().required('El usuario es requerido'),
    password: Yup.string().required('La contraseña es requerida')
});

const Login: React.FC = () => {
    const navigate = useNavigate();
    const { login } = useAuth();

    return (
        <Container component="main" maxWidth="xs">
            <Paper elevation={3} sx={{ p: 4, mt: 8 }}>
                <Typography component="h1" variant="h5" align="center">
                    Iniciar Sesión
                </Typography>
                <Formik
                    initialValues={{ username: '', password: '' }}
                    validationSchema={validationSchema}
                    onSubmit={async (values, { setSubmitting, setStatus }) => {
                        try {
                            const response = await authService.login(
                                values.username,
                                values.password
                            );
                            login(response.token, response.user);
                            navigate('/subastas');
                        } catch (error) {
                            setStatus('Credenciales inválidas');
                        } finally {
                            setSubmitting(false);
                        }
                    }}
                >
                    {({
                        values,
                        errors,
                        touched,
                        handleChange,
                        handleBlur,
                        handleSubmit,
                        isSubmitting,
                        status
                    }) => (
                        <Form onSubmit={handleSubmit}>
                            {status && (
                                <Alert severity="error" sx={{ mb: 2 }}>
                                    {status}
                                </Alert>
                            )}
                            <TextField
                                fullWidth
                                margin="normal"
                                name="username"
                                label="Usuario"
                                value={values.username}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                error={touched.username && Boolean(errors.username)}
                                helperText={touched.username && errors.username}
                            />
                            <TextField
                                fullWidth
                                margin="normal"
                                name="password"
                                label="Contraseña"
                                type="password"
                                value={values.password}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                error={touched.password && Boolean(errors.password)}
                                helperText={touched.password && errors.password}
                            />
                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                sx={{ mt: 3, mb: 2 }}
                                disabled={isSubmitting}
                            >
                                Iniciar Sesión
                            </Button>
                            <Box textAlign="center">
                                <Button
                                    color="primary"
                                    onClick={() => navigate('/registro')}
                                >
                                    ¿No tienes cuenta? Regístrate
                                </Button>
                            </Box>
                        </Form>
                    )}
                </Formik>
            </Paper>
        </Container>
    );
};

export default Login; 