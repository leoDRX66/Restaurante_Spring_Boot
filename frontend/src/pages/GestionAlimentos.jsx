import React, { useState, useEffect } from 'react';

export default function GestionAlimentos() {
    const [alimentos, setAlimentos] = useState([]);
    const [formData, setFormData] = useState({ nombre: '', precio: '', tipo: 'PlatoFuerte' });

    const fetchAlimentos = async () => {
        try {
            const res = await fetch('http://localhost:8080/api/alimentos');
            if (res.ok) setAlimentos(await res.json());
        } catch (err) {
            console.error("Error al cargar alimentos", err);
        }
    };

    useEffect(() => { fetchAlimentos(); }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/api/alimentos', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ ...formData, precio: parseFloat(formData.precio) })
            });

            if (response.ok) {
                setFormData({ nombre: '', precio: '', tipo: 'PlatoFuerte' });
                fetchAlimentos();
            } else {
                alert("Error 400: El formato de los datos no es válido para el servidor. Revisa la consola.");
            }
        } catch (err) {
            alert("Error al guardar en el servidor");
        }
    };

    const handleEliminar = async (id) => {
        if (window.confirm("¿Seguro que deseas eliminar este alimento?")) {
            await fetch(`http://localhost:8080/api/alimentos/${id}`, { method: 'DELETE' });
            fetchAlimentos();
        }
    };

    // Estilo unificado para inputs y selects para evitar inconsistencias visuales
    const inputStyle = {
        width: '100%',
        padding: '8px 12px', // Ligeramente más padding para mejor alineación
        border: '1px solid #cbd5e1',
        borderRadius: '4px',
        backgroundColor: '#ffffff', // Fuerza el fondo blanco
        color: '#0f172a', // Fuerza el texto oscuro
        boxSizing: 'border-box', // Crucial: asegura que el padding no desborde el width
        outline: 'none', // Quita el borde naranja por defecto de algunos navegadores al hacer clic
        fontSize: '14px'
    };

    return (
        <div>
            <h2 style={{ color: '#1e3a8a', marginBottom: '20px' }}>Gestión de Alimentos</h2>

            <div style={{ display: 'grid', gridTemplateColumns: '300px 1fr', gap: '30px' }}>
                {/* Formulario Lateral */}
                <div style={{ backgroundColor: '#ffffff', padding: '20px', borderRadius: '8px', border: '1px solid #e2e8f0' }}>
                    <h3 style={{ marginTop: 0, color: '#1e3a8a' }}>Nuevo Registro</h3>
                    <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
                        <div>
                            <label style={{ display: 'block', fontSize: '14px', marginBottom: '5px', fontWeight: '500', color: '#475569' }}>Nombre:</label>
                            <input
                                required
                                type="text"
                                value={formData.nombre}
                                onChange={e => setFormData({...formData, nombre: e.target.value})}
                                style={inputStyle}
                            />
                        </div>
                        <div>
                            <label style={{ display: 'block', fontSize: '14px', marginBottom: '5px', fontWeight: '500', color: '#475569' }}>Precio ($):</label>
                            <input
                                required
                                type="number"
                                step="0.01"
                                value={formData.precio}
                                onChange={e => setFormData({...formData, precio: e.target.value})}
                                style={inputStyle}
                            />
                        </div>
                        <div>
                            <label style={{ display: 'block', fontSize: '14px', marginBottom: '5px', fontWeight: '500', color: '#475569' }}>Tipo:</label>
                            <select
                                value={formData.tipo}
                                onChange={e => setFormData({...formData, tipo: e.target.value})}
                                style={inputStyle}
                            >
                                <option value="PlatoFuerte">Plato Fuerte</option>
                                <option value="Bebida">Bebida</option>
                                <option value="Postre">Postre</option>
                                <option value="Adicional">Adicionales</option>
                            </select>
                        </div>
                        <button type="submit" style={{ backgroundColor: '#22c55e', color: 'white', padding: '10px', border: 'none', borderRadius: '4px', fontWeight: 'bold', cursor: 'pointer', marginTop: '10px' }}>
                            Guardar Alimento
                        </button>
                    </form>
                </div>

                {/* Tabla Central */}
                <div style={{ overflowX: 'auto', border: '1px solid #e2e8f0', borderRadius: '8px' }}>
                    <table style={{ width: '100%', borderCollapse: 'collapse', backgroundColor: '#ffffff' }}>
                        <thead>
                        <tr>
                            <th style={{ backgroundColor: '#f1f5f9', color: '#475569', padding: '12px', borderBottom: '1px solid #cbd5e1', textAlign: 'left' }}>ID</th>
                            <th style={{ backgroundColor: '#f1f5f9', color: '#475569', padding: '12px', borderBottom: '1px solid #cbd5e1', textAlign: 'left' }}>Nombre</th>
                            <th style={{ backgroundColor: '#f1f5f9', color: '#475569', padding: '12px', borderBottom: '1px solid #cbd5e1', textAlign: 'left' }}>Tipo</th>
                            <th style={{ backgroundColor: '#f1f5f9', color: '#475569', padding: '12px', borderBottom: '1px solid #cbd5e1', textAlign: 'left' }}>Precio</th>
                            <th style={{ backgroundColor: '#f1f5f9', color: '#475569', padding: '12px', borderBottom: '1px solid #cbd5e1', textAlign: 'center' }}>Acción</th>
                        </tr>
                        </thead>
                        <tbody>
                        {alimentos.length === 0 ? (
                            <tr>
                                <td colSpan="5" style={{ padding: '20px', textAlign: 'center', color: '#64748b' }}>No hay alimentos registrados.</td>
                            </tr>
                        ) : (
                            alimentos.map(a => (
                                <tr key={a.id} style={{ borderBottom: '1px solid #f1f5f9' }}>
                                    <td style={{ padding: '12px 10px', color: '#334155' }}>{a.id}</td>
                                    <td style={{ padding: '12px 10px', color: '#334155', fontWeight: '500' }}>{a.nombre}</td>
                                    <td style={{ padding: '12px 10px', color: '#64748b' }}>{a.tipo}</td>
                                    <td style={{ padding: '12px 10px', color: '#16a34a', fontWeight: 'bold' }}>${a.precio}</td>
                                    <td style={{ padding: '12px 10px', textAlign: 'center' }}>
                                        <button
                                            onClick={() => handleEliminar(a.id)}
                                            style={{ backgroundColor: '#ef4444', color: 'white', border: 'none', padding: '6px 12px', borderRadius: '4px', cursor: 'pointer', fontSize: '12px', fontWeight: 'bold' }}
                                        >
                                            Eliminar
                                        </button>
                                    </td>
                                </tr>
                            ))
                        )}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}