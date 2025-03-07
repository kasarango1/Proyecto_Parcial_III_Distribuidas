import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { ThemeProvider, createTheme, CssBaseline } from '@mui/material';
import { AuthProvider } from './context/AuthContext';
import Login from './pages/Login';
import Subastas from './pages/Subastas';
import DetalleSubasta from './pages/DetalleSubasta';
import { useAuth } from './context/AuthContext';

const theme = createTheme({
    palette: {
        mode: 'light',
        primary: {
            main: '#1976d2',
        },
        secondary: {
            main: '#dc004e',
        },
    },
});

const ProtectedRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const { isAuthenticated } = useAuth();
    
    if (!isAuthenticated) {
        return <Navigate to="/login" />;
    }

    return <>{children}</>;
};

const App: React.FC = () => {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <AuthProvider>
                <Router>
                    <Routes>
                        <Route path="/login" element={<Login />} />
                        <Route
                            path="/subastas"
                            element={
                                <ProtectedRoute>
                                    <Subastas />
                                </ProtectedRoute>
                            }
                        />
                        <Route
                            path="/subastas/:id"
                            element={
                                <ProtectedRoute>
                                    <DetalleSubasta />
                                </ProtectedRoute>
                            }
                        />
                        <Route path="/" element={<Navigate to="/subastas" />} />
                    </Routes>
                </Router>
            </AuthProvider>
        </ThemeProvider>
    );
};

export default App; 