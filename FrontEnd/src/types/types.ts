export interface User {
    id: number;
    username: string;
    email: string;
    role: 'VENDEDOR' | 'COMPRADOR' | 'ADMIN';
}

export interface Auto {
    id: number;
    marca: string;
    modelo: string;
    año: number;
    precioBase: number;
    descripcion: string;
    imagenUrl?: string;
    vendedorId: number;
    estado: 'DISPONIBLE' | 'SUBASTADO' | 'VENDIDO';
}

export interface Subasta {
    id: number;
    fechaInicio: string;
    fechaFin: string;
    estado: 'PENDIENTE' | 'ACTIVA' | 'FINALIZADA';
    autos: Auto[];
}

export interface Puja {
    id: number;
    subastaId: number;
    autoId: number;
    compradorId: number;
    monto: number;
    fecha: string;
}

export interface AuthResponse {
    token: string;
    user: User;
} 