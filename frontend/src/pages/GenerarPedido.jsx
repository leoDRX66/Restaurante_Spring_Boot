import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, LogOut, Plus, Trash2 } from 'lucide-react';

export default function GenerarPedido() {

    const navigate = useNavigate();

    const [combos, setCombos] = useState({
        chefs: [],
        mozos: [],
        alimentos: []
    });

    const [pedidoForm, setPedidoForm] = useState({
        chefId: '',
        meseroId: '',
        alimentoId: '',
        numeroMesa: ''
    });

    // NUEVO ESTADO PARA LA TABLA
    const [pedidos, setPedidos] = useState([]);

    useEffect(() => {

        const fetchSeguro = async (url) => {
            try {
                const res = await fetch(url);

                if (!res.ok) return [];

                const data = await res.json();

                return Array.isArray(data) ? data : [];

            } catch (error) {
                console.error(`Error de conexión con ${url}`, error);
                return [];
            }
        };

        const cargarDatos = async () => {

            const [c, m, a] = await Promise.all([
                fetchSeguro('http://localhost:8080/api/chefs'),
                fetchSeguro('http://localhost:8080/api/meseros'),
                fetchSeguro('http://localhost:8080/api/alimentos')
            ]);

            setCombos({
                chefs: c,
                mozos: m,
                alimentos: a
            });
        };

        cargarDatos();

    }, []);

    // AGREGAR PEDIDO A LA TABLA
    const agregarPedido = () => {

        if (
            !pedidoForm.chefId ||
            !pedidoForm.meseroId ||
            !pedidoForm.alimentoId ||
            !pedidoForm.numeroMesa
        ) {
            alert("Completa todos los campos.");
            return;
        }

        const chefSeleccionado = combos.chefs.find(
            c => c.id === parseInt(pedidoForm.chefId)
        );

        const mozoSeleccionado = combos.mozos.find(
            m => m.id === parseInt(pedidoForm.meseroId)
        );

        const alimentoSeleccionado = combos.alimentos.find(
            a => a.id === parseInt(pedidoForm.alimentoId)
        );

        const nuevoPedido = {
            id: pedidos.length + 1,
            chef: chefSeleccionado.nombre,
            mozo: mozoSeleccionado.nombre,
            plato: alimentoSeleccionado.nombre,
            total: alimentoSeleccionado.precio,
            mesa: pedidoForm.numeroMesa
        };

        setPedidos([...pedidos, nuevoPedido]);

        setPedidoForm({
            chefId: '',
            meseroId: '',
            alimentoId: '',
            numeroMesa: ''
        });
    };

    // ELIMINAR PEDIDO
    const eliminarPedido = (id) => {
        setPedidos(pedidos.filter(p => p.id !== id));
    };

    // ENVIAR AL BACKEND
    const confirmarPedidos = async () => {

        if (pedidos.length === 0) {
            alert("No hay pedidos cargados.");
            return;
        }

        try {

            for (const pedido of pedidos) {

                await fetch('http://localhost:8080/api/pedidos', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        chefId: combos.chefs.find(c => c.nombre === pedido.chef)?.id,
                        meseroId: combos.mozos.find(m => m.nombre === pedido.mozo)?.id,
                        alimentoId: combos.alimentos.find(a => a.nombre === pedido.plato)?.id,
                        numeroMesa: parseInt(pedido.mesa)
                    })
                });
            }

            alert("Pedidos confirmados correctamente.");

            setPedidos([]);

        } catch (error) {

            alert("Error al guardar pedidos.");

        }
    };


// Estilo corregido para forzar el texto oscuro y alinear los bordes
    const inputStyle = {
        width: '100%',
        padding: '10px 12px',
        border: '1px solid #cbd5e1',
        borderRadius: '4px',
        backgroundColor: '#ffffff', // Fondo blanco
        color: '#0f172a',           // <--- ESTA ES LA CLAVE: Texto azul oscuro/casi negro
        boxSizing: 'border-box',    // Evita que el padding rompa el ancho
        outline: 'none',
        fontSize: '14px'
    };

    const labelStyle = {
        fontWeight: 'bold',
        marginBottom: '8px',
        display: 'block',
        color: '#374151'
    };

    return (

        <div
            style={{
                maxWidth: '1200px',
                margin: '0 auto',
                padding: '20px',
                fontFamily: 'Arial'
            }}
        >

            {/* TITULO */}

            <h2
                style={{
                    textAlign: 'center',
                    marginBottom: '25px',
                    color: '#1e293b'
                }}
            >
                Generador de pedido final
            </h2>

            {/* PANEL SUPERIOR */}

            <fieldset
                style={{
                    border: '1px solid #cbd5e1',
                    padding: '20px',
                    marginBottom: '30px'
                }}
            >

                <legend
                    style={{
                        fontWeight: 'bold'
                    }}
                >
                    Datos del Pedido
                </legend>

                <div
                    style={{
                        display: 'grid',
                        gridTemplateColumns: 'repeat(5, 1fr)',
                        gap: '14px'
                    }}
                >

                    {/* CHEF */}

                    <div>

                        <label style={labelStyle}>
                            Chef
                        </label>

                        <select
                            style={inputStyle}
                            value={pedidoForm.chefId}
                            onChange={(e) =>
                                setPedidoForm({
                                    ...pedidoForm,
                                    chefId: e.target.value
                                })
                            }
                        >

                            <option value="">
                                Seleccione
                            </option>

                            {combos.chefs.map(c => (
                                <option
                                    key={c.id}
                                    value={c.id}
                                >
                                    {c.nombre}
                                </option>
                            ))}

                        </select>

                    </div>

                    {/* MOZO */}

                    <div>

                        <label style={labelStyle}>
                            Mozo
                        </label>

                        <select
                            style={inputStyle}
                            value={pedidoForm.meseroId}
                            onChange={(e) =>
                                setPedidoForm({
                                    ...pedidoForm,
                                    meseroId: e.target.value
                                })
                            }
                        >

                            <option value="">
                                Seleccione
                            </option>

                            {combos.mozos.map(m => (
                                <option
                                    key={m.id}
                                    value={m.id}
                                >
                                    {m.nombre}
                                </option>
                            ))}

                        </select>

                    </div>

                    {/* MESA */}

                    <div>

                        <label style={labelStyle}>
                            Cliente (mesa)
                        </label>

                        <input
                            type="number"
                            placeholder="Numero de mesa"
                            style={inputStyle}
                            value={pedidoForm.numeroMesa}
                            onChange={(e) =>
                                setPedidoForm({
                                    ...pedidoForm,
                                    numeroMesa: e.target.value
                                })
                            }
                        />

                    </div>

                    {/* PLATO */}

                    <div>

                        <label style={labelStyle}>
                            Plato
                        </label>

                        <select
                            style={inputStyle}
                            value={pedidoForm.alimentoId}
                            onChange={(e) =>
                                setPedidoForm({
                                    ...pedidoForm,
                                    alimentoId: e.target.value
                                })
                            }
                        >

                            <option value="">
                                Seleccione
                            </option>

                            {combos.alimentos.map(a => (
                                <option
                                    key={a.id}
                                    value={a.id}
                                >
                                    {a.nombre} (${a.precio})
                                </option>
                            ))}

                        </select>

                    </div>

                    {/* BOTON AGREGAR */}

                    <div
                        style={{
                            display: 'flex',
                            alignItems: 'end'
                        }}
                    >

                        <button
                            onClick={agregarPedido}
                            style={{
                                width: '100%',
                                backgroundColor: '#22c55e',
                                color: 'white',
                                border: 'none',
                                padding: '12px',
                                cursor: 'pointer',
                                fontWeight: 'bold',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                                gap: '8px'
                            }}
                        >

                            <Plus size={18} />

                            AGREGAR

                        </button>

                    </div>

                </div>

            </fieldset>

            {/* TABLA */}

            <fieldset
                style={{
                    border: '1px solid #cbd5e1',
                    padding: '10px'
                }}
            >

                <legend
                    style={{
                        fontWeight: 'bold'
                    }}
                >
                    Historial de Pedidos Pendientes
                </legend>

                <table
                    style={{
                        width: '100%',
                        borderCollapse: 'collapse'
                    }}
                >

                    <thead>

                    <tr
                        style={{
                            backgroundColor: '#e2e8f0'
                        }}
                    >

                        <th style={thStyle}>Plato</th>
                        <th style={thStyle}>Mozo</th>
                        <th style={thStyle}>Mesa</th>
                        <th style={thStyle}>Chef</th>
                        <th style={thStyle}>Total ($)</th>
                        <th style={thStyle}>Acción</th>
                        <th style={thStyle}>Entregar Pedido</th>

                    </tr>

                    </thead>

                    <tbody>

                    {pedidos.map((pedido) => (

                        <tr key={pedido.id}>

                            <td style={tdStyle}>{pedido.id}</td>
                            <td style={tdStyle}>{pedido.plato}</td>
                            <td style={tdStyle}>{pedido.mozo}</td>
                            <td style={tdStyle}>{pedido.mesa}</td>
                            <td style={tdStyle}>{pedido.chef}</td>
                            <td style={tdStyle}>${pedido.total}</td>

                            <td style={tdStyle}>

                                <button
                                    onClick={() => eliminarPedido(pedido.id)}
                                    style={{
                                        backgroundColor: '#ef4444',
                                        border: 'none',
                                        color: 'white',
                                        padding: '8px 12px',
                                        cursor: 'pointer'
                                    }}
                                >

                                    <Trash2 size={16} />

                                </button>

                            </td>
                            <td style={tdStyle}>

                                <button
                                    onClick={() => eliminarPedido(pedido.id)}
                                    style={{
                                        backgroundColor: '#22c55e',
                                        border: 'none',
                                        color: 'white',
                                        padding: '8px 14px',
                                        cursor: 'pointer',
                                        fontWeight: 'bold',
                                        borderRadius: '4px'
                                    }}
                                >

                                    ENTREGAR

                                </button>

                            </td>

                        </tr>

                    ))}

                    </tbody>

                </table>

            </fieldset>

            {/* BOTONES INFERIORES */}

            <div
                style={{
                    display: 'flex',
                    justifyContent: 'center',
                    gap: '20px',
                    marginTop: '30px'
                }}
            >

                <button
                    onClick={() => navigate('/')}
                    style={{
                        display: 'flex',
                        alignItems: 'center',
                        gap: '8px',
                        backgroundColor: '#3b82f6',
                        color: 'white',
                        padding: '10px 20px',
                        border: 'none',
                        cursor: 'pointer'
                    }}
                >

                    <ArrowLeft size={16} />

                    Volver

                </button>

                <button
                    onClick={() => alert('Sesión finalizada')}
                    style={{
                        display: 'flex',
                        alignItems: 'center',
                        gap: '8px',
                        backgroundColor: '#ef4444',
                        color: 'white',
                        padding: '10px 20px',
                        border: 'none',
                        cursor: 'pointer'
                    }}
                >

                    <LogOut size={16} />

                    Salir

                </button>

            </div>

        </div>
    );
}

const thStyle = {
    border: '1px solid #cbd5e1',
    padding: '10px',
    fontWeight: 'bold'
};

const tdStyle = {
    border: '1px solid #cbd5e1',
    padding: '10px',
    textAlign: 'center'
};