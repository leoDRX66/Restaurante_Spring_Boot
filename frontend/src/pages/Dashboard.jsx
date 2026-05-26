import React from 'react';
import { Utensils, Users, ClipboardList } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

export default function Dashboard() {
    const navigate = useNavigate();

    return (
        <div>
            <h1 style={{ color: '#1e3a8a', marginBottom: '20px' }}>Panel Principal</h1>
            <p style={{ color: '#64748b', marginBottom: '30px' }}>
                Bienvenido al Sistema de Gestión del Restaurante.
            </p>

            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))', gap: '20px' }}>

                {/* Tarjeta de Alimentos */}
                <div
                    onClick={() => navigate('/alimentos')}
                    style={{ backgroundColor: '#ffffff', padding: '20px', borderRadius: '8px', boxShadow: '0 2px 4px rgba(0,0,0,0.1)', cursor: 'pointer', borderTop: '4px solid #3b82f6' }}
                >
                    <Utensils size={32} color="#3b82f6" style={{ marginBottom: '10px' }} />
                    <h3 style={{ margin: '0 0 10px 0' }}>Gestión de Alimentos</h3>
                    <p style={{ margin: '0', color: '#64748b', fontSize: '14px' }}>Alta, baja y modificación de platos y bebidas.</p>
                </div>

                {/* Tarjeta de Pedidos */}
                <div
                    onClick={() => navigate('/pedidos')}
                    style={{ backgroundColor: '#ffffff', padding: '20px', borderRadius: '8px', boxShadow: '0 2px 4px rgba(0,0,0,0.1)', cursor: 'pointer', borderTop: '4px solid #2ecc71' }}
                >
                    <ClipboardList size={32} color="#2ecc71" style={{ marginBottom: '10px' }} />
                    <h3 style={{ margin: '0 0 10px 0' }}>Generar Pedido</h3>
                    <p style={{ margin: '0', color: '#64748b', fontSize: '14px' }}>Asignar comandas a chefs y mesas a mozos.</p>
                </div>

                {/* Tarjeta de Personal */}
                <div
                    onClick={() => navigate('/personal')}
                    style={{ backgroundColor: '#ffffff', padding: '20px', borderRadius: '8px', boxShadow: '0 2px 4px rgba(0,0,0,0.1)', cursor: 'pointer', borderTop: '4px solid #f59e0b' }}
                >
                    <Users size={32} color="#f59e0b" style={{ marginBottom: '10px' }} />
                    <h3 style={{ margin: '0 0 10px 0' }}>Personal Activo</h3>
                    <p style={{ margin: '0', color: '#64748b', fontSize: '14px' }}>Visualiza la lista de chefs y meseros trabajando.</p>
                </div>

            </div>
        </div>
    );
}