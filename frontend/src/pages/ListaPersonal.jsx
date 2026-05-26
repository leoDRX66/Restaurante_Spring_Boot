import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, LogOut } from 'lucide-react';

export default function ListaPersonal() {
    const navigate = useNavigate();
    const [chefs, setChefs] = useState([]);
    const [mozos, setMozos] = useState([]);

    useEffect(() => {
        // Función de seguridad para evitar que la página colapse si el backend falla
        const fetchSeguro = async (url) => {
            try {
                const res = await fetch(url);
                if (!res.ok) {
                    console.warn(`Error en servidor: ${res.status} para la ruta ${url}`);
                    return [];
                }
                const data = await res.json();
                return Array.isArray(data) ? data : []; // Garantiza que SIEMPRE sea un Array
            } catch (error) {
                console.error(`Error de conexión con ${url}`, error);
                return [];
            }
        };

        const fetchData = async () => {
            // Llamamos a las rutas de forma segura
            const dataChefs = await fetchSeguro('http://localhost:8080/api/chefs');
            const dataMozos = await fetchSeguro('http://localhost:8080/api/meseros');

            setChefs(dataChefs);
            setMozos(dataMozos);
        };

        fetchData();
    }, []);

    // Estilos adaptados a tu nueva imagen
    const tableStyle = { width: '100%', borderCollapse: 'collapse', backgroundColor: '#ffffff', fontSize: '14px', marginBottom: '30px' };
    const thStyle = { backgroundColor: '#1e3a8a', color: 'white', padding: '12px', border: '1px solid #cbd5e1', textAlign: 'left', fontWeight: 'bold' };
    const tdStyle = { padding: '10px', border: '1px solid #cbd5e1', backgroundColor: '#e0f2fe' };
    const sectionTitleStyle = { color: '#1e3a8a', borderBottom: '2px solid #3b82f6', paddingBottom: '5px', marginTop: '10px', marginBottom: '15px' };

    return (
        <div style={{ display: 'flex', flexDirection: 'column', height: '100%', maxWidth: '900px', margin: '0 auto' }}>

            {/* Encabezado Principal (Barra azul oscuro) */}
            <div style={{ backgroundColor: '#1e3a8a', padding: '15px', borderRadius: '4px', marginBottom: '30px', boxShadow: '0 4px 6px rgba(0,0,0,0.1)' }}>
                <h2 style={{ textAlign: 'center', color: '#ffffff', margin: 0, fontFamily: '"Arial Black", Gadget, sans-serif', letterSpacing: '1px' }}>
                    Registro de Personal Trabajando
                </h2>
            </div>

            <div style={{ flex: 1, overflowY: 'auto', paddingRight: '10px' }}>

                {/* Sección CHEFS (Tabla Superior) */}
                <h3 style={sectionTitleStyle}>CHEFS</h3>
                <div style={{ overflowX: 'auto', border: '1px solid #cbd5e1', borderRadius: '4px', boxShadow: '0 2px 4px rgba(0,0,0,0.05)' }}>
                    <table style={{...tableStyle, marginBottom: 0}}>
                        <thead>
                        <tr>
                            <th style={thStyle}>Nombre</th>
                            <th style={thStyle}>Turno</th>
                            <th style={thStyle}>Especialidad</th>
                            <th style={thStyle}>Años de experiencia</th>
                        </tr>
                        </thead>
                        <tbody>
                        {chefs.length === 0 ? (
                            <tr><td colSpan="4" style={{...tdStyle, backgroundColor: '#fff', textAlign: 'center'}}>No hay chefs registrados en el sistema.</td></tr>
                        ) : (
                            chefs.map(chef => (
                                <tr key={chef.id}>
                                    <td style={tdStyle}>{chef.nombre}</td>
                                    <td style={tdStyle}>{chef.turno}</td>
                                    <td style={tdStyle}>{chef.especialidad}</td>
                                    <td style={tdStyle}>{chef.anosExperiencia}</td>
                                </tr>
                            ))
                        )}
                        </tbody>
                    </table>
                </div>

                <div style={{ height: '30px' }}></div> {/* Espaciador entre tablas */}

                {/* Sección MOZOS (Tabla Inferior) */}
                <h3 style={sectionTitleStyle}>MOZOS</h3>
                <div style={{ overflowX: 'auto', border: '1px solid #cbd5e1', borderRadius: '4px', boxShadow: '0 2px 4px rgba(0,0,0,0.05)' }}>
                    <table style={{...tableStyle, marginBottom: 0}}>
                        <thead>
                        <tr>
                            <th style={thStyle}>Nombre</th>
                            <th style={thStyle}>Turno</th>
                            <th style={thStyle}>Nivel</th>
                            <th style={thStyle}>Área Asignada</th>
                        </tr>
                        </thead>
                        <tbody>
                        {mozos.length === 0 ? (
                            <tr><td colSpan="4" style={{...tdStyle, backgroundColor: '#fff', textAlign: 'center'}}>No hay mozos registrados en el sistema.</td></tr>
                        ) : (
                            mozos.map(mozo => (
                                <tr key={mozo.id}>
                                    <td style={tdStyle}>{mozo.nombre}</td>
                                    <td style={tdStyle}>{mozo.turno}</td>
                                    <td style={tdStyle}>{mozo.nivel}</td>
                                    <td style={tdStyle}>{mozo.areaAsignada}</td>
                                </tr>
                            ))
                        )}
                        </tbody>
                    </table>
                </div>

            </div>

            {/* Botones inferiores */}
            <div style={{ display: 'flex', justifyContent: 'center', gap: '20px', marginTop: '30px', paddingBottom: '20px' }}>
                <button
                    onClick={() => navigate('/')}
                    style={{ display: 'flex', alignItems: 'center', gap: '8px', backgroundColor: '#3b82f6', color: 'white', padding: '10px 20px', border: 'none', borderRadius: '4px', fontWeight: 'bold', cursor: 'pointer', fontSize: '14px' }}
                >
                    <ArrowLeft size={16} /> Volver al Menú
                </button>
                <button
                    onClick={() => alert('Sesión finalizada corporativamente.')}
                    style={{ display: 'flex', alignItems: 'center', gap: '8px', backgroundColor: '#ef4444', color: 'white', padding: '10px 20px', border: 'none', borderRadius: '4px', fontWeight: 'bold', cursor: 'pointer', fontSize: '14px' }}
                >
                    <LogOut size={16} /> Salir
                </button>
            </div>
        </div>
    );
}