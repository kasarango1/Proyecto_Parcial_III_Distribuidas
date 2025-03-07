import axios from 'axios';
import { Auto, Subasta, Puja, AuthResponse } from '../types/types';

const API_URL = 'http://localhost:8080/api'; // Ajusta esto según tu configuración

const api = axios.create({
    baseURL: API_URL,
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export const authService = {
    login: async (username: string, password: string): Promise<AuthResponse> => {
        const response = await api.post('/auth/login', { username, password });
        return response.data;
    },
    register: async (username: string, email: string, password: string, role: string): Promise<AuthResponse> => {
        const response = await api.post('/auth/register', { username, email, password, role });
        return response.data;
    }
};

export const subastaService = {
    getSubastasActivas: async (): Promise<Subasta[]> => {
        const response = await api.get('/subastas/activas');
        return response.data;
    },
    getSubasta: async (id: number): Promise<Subasta> => {
        const response = await api.get(`/subastas/${id}`);
        return response.data;
    },
    crearSubasta: async (subasta: Partial<Subasta>): Promise<Subasta> => {
        const response = await api.post('/subastas', subasta);
        return response.data;
    }
};

export const autoService = {
    getAutos: async (): Promise<Auto[]> => {
        const response = await api.get('/autos');
        return response.data;
    },
    crearAuto: async (auto: Partial<Auto>): Promise<Auto> => {
        const response = await api.post('/autos', auto);
        return response.data;
    },
    actualizarAuto: async (id: number, auto: Partial<Auto>): Promise<Auto> => {
        const response = await api.put(`/autos/${id}`, auto);
        return response.data;
    }
};

export const pujaService = {
    crearPuja: async (puja: Partial<Puja>): Promise<Puja> => {
        const response = await api.post('/pujas', puja);
        return response.data;
    },
    getPujasPorSubasta: async (subastaId: number): Promise<Puja[]> => {
        const response = await api.get(`/pujas/subasta/${subastaId}`);
        return response.data;
    }
}; 