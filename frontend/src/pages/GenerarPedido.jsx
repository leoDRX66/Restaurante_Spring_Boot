import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, LogOut, Plus, Trash2 } from 'lucide-react';

const API = 'http://localhost:8080/api';

export default function GenerarPedido() {
    const navigate = useNavigate();

    // ── Combos cargados del backend ──────────────────────────────────────────
    const [combos, setCombos] = useState({ chefs: [], mozos: [], alimentos: [] });

    // ── Formulario para armar UN pedido ─────────────────────────────────────
    const FORM_VACIO = { chefId: '', meseroId: '', platoId: '', bebidaId: '', numeroMesa: '' };
    const [pedidoForm, setPedidoForm] = useState(FORM_VACIO);

    // ── Tabla local de pedidos pendientes (aún no enviados) ──────────────────
    const [pendientes, setPendientes] = useState([]);

    // ── Pedidos ya confirmados y persistidos en BD ───────────────────────────
    const [confirmados, setConfirmados] = useState([]);

    const [cargando, setCargando] = useState(false);
    const [mensaje, setMensaje] = useState(null); // { tipo: 'ok'|'error', texto }

    // ── Carga inicial de combos y pedidos guardados ──────────────────────────
    useEffect(() => {
        const fetchSeguro = async (url) => {
            try {
                const res = await fetch(url);
                if (!res.ok) return [];
                const data = await res.json();
                return Array.isArray(data) ? data : [];
            } catch {
                return [];
            }
        };

        const cargarTodo = async () => {
            const [c, m, a, p] = await Promise.all([
                fetchSeguro(`${API}/chefs`),
                fetchSeguro(`${API}/meseros`),
                fetchSeguro(`${API}/alimentos`),
                fetchSeguro(`${API}/pedidos`),   // ← carga pedidos ya en BD
            ]);
            setCombos({ chefs: c, mozos: m, alimentos: a });
            setConfirmados(p);
        };

        cargarTodo();
    }, []);

    // ── Helpers para obtener labels a partir de IDs ──────────────────────────
    const nombreChef    = (id) => combos.chefs.find(c => c.id === id)?.nombre    ?? '—';
    const nombreMozo    = (id) => combos.mozos.find(m => m.id === id)?.nombre    ?? '—';
    const nombreAlimento= (id) => combos.alimentos.find(a => a.id === id)?.nombre ?? '—';
    const precioAlimento= (id) => combos.alimentos.find(a => a.id === id)?.precio ?? 0;

    // ── Mostrar mensaje temporal ─────────────────────────────────────────────
    const mostrarMensaje = (tipo, texto) => {
        setMensaje({ tipo, texto });
        setTimeout(() => setMensaje(null), 4000);
    };

    // ── AGREGAR a la lista local (todavía NO va al backend) ──────────────────
    const agregarPedido = () => {
        const { chefId, meseroId, platoId, numeroMesa } = pedidoForm;
        if (!chefId || !meseroId || !platoId || !numeroMesa) {
            mostrarMensaje('error', 'Completa Chef, Mozo, Mesa y Plato antes de agregar.');
            return;
        }

        const total =
            precioAlimento(parseInt(platoId)) +
            (pedidoForm.bebidaId ? precioAlimento(parseInt(pedidoForm.bebidaId)) : 0);

        setPendientes(prev => [...prev, {
            _tmpId:     Date.now(),          // clave local temporal
            chefId:     parseInt(chefId),
            meseroId:   parseInt(meseroId),
            platoId:    parseInt(platoId),
            bebidaId:   pedidoForm.bebidaId ? parseInt(pedidoForm.bebidaId) : null,
            numeroMesa: parseInt(numeroMesa),
            total,
        }]);

        setPedidoForm(FORM_VACIO);
    };

    // ── QUITAR de la lista local (sin tocar BD) ──────────────────────────────
    const quitarPendiente = (tmpId) =>
        setPendientes(prev => prev.filter(p => p._tmpId !== tmpId));

    // ── CONFIRMAR: persiste todos los pendientes en la BD ────────────────────
    const confirmarPedidos = async () => {
        if (pendientes.length === 0) {
            mostrarMensaje('error', 'No hay pedidos pendientes para confirmar.');
            return;
        }
        setCargando(true);
        try {
            for (const p of pendientes) {
                const body = {
                    chefId:     p.chefId,
                    meseroId:   p.meseroId,
                    platoId:    p.platoId,          // ← campo correcto para el backend
                    bebidaId:   p.bebidaId ?? null,
                    numeroMesa: p.numeroMesa,
                };

                const res = await fetch(`${API}/pedidos`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(body),
                });

                if (!res.ok) {
                    const errText = await res.text();
                    throw new Error(`Error al guardar pedido mesa ${p.numeroMesa}: ${errText}`);
                }
            }

            // Recarga los confirmados desde la BD
            const actualizados = await fetch(`${API}/pedidos`).then(r => r.json()).catch(() => []);
            setConfirmados(Array.isArray(actualizados) ? actualizados : []);

            setPendientes([]);
            mostrarMensaje('ok', `✅ ${pendientes.length} pedido(s) guardado(s) correctamente.`);
        } catch (err) {
            mostrarMensaje('error', `❌ ${err.message}`);
        } finally {
            setCargando(false);
        }
    };

    // ── ENTREGAR: elimina el pedido de la BD y lo quita de la vista ──────────
    const entregarPedido = async (id) => {
        if (!window.confirm('¿Confirmar entrega y eliminar este pedido de la BD?')) return;
        try {
            const res = await fetch(`${API}/pedidos/${id}`, { method: 'DELETE' });
            if (!res.ok) throw new Error(await res.text());
            setConfirmados(prev => prev.filter(p => p.id !== id));
            mostrarMensaje('ok', `✅ Pedido #${id} entregado y eliminado.`);
        } catch (err) {
            mostrarMensaje('error', `❌ ${err.message}`);
        }
    };

    // ── Separar alimentos por tipo para los combos ───────────────────────────
    const platos  = combos.alimentos.filter(a => a.tipo !== 'Bebida');
    const bebidas = combos.alimentos.filter(a => a.tipo === 'Bebida');

    // ────────────────────────────────────────────────────────────────────────
    return (
        <div style={{ maxWidth: '1100px', margin: '0 auto', padding: '20px', fontFamily: 'Arial' }}>

            <h2 style={{ textAlign: 'center', marginBottom: '20px', color: '#1e293b' }}>
                Generador de Pedidos
            </h2>

            {/* Mensaje de estado */}
            {mensaje && (
                <div style={{
                    padding: '12px 16px', borderRadius: '6px', marginBottom: '16px',
                    backgroundColor: mensaje.tipo === 'ok' ? '#dcfce7' : '#fee2e2',
                    color: mensaje.tipo === 'ok' ? '#166534' : '#991b1b',
                    fontWeight: 'bold', border: `1px solid ${mensaje.tipo === 'ok' ? '#86efac' : '#fca5a5'}`
                }}>
                    {mensaje.texto}
                </div>
            )}

            {/* ── Panel de formulario ── */}
            <fieldset style={{ border: '1px solid #cbd5e1', padding: '20px', marginBottom: '24px', borderRadius: '6px' }}>
                <legend style={{ fontWeight: 'bold', padding: '0 8px' }}>Nuevo Pedido</legend>

                <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(150px, 1fr))', gap: '14px' }}>

                    {/* Chef */}
                    <div>
                        <label style={labelStyle}>Chef</label>
                        <select style={inputStyle} value={pedidoForm.chefId}
                                onChange={e => setPedidoForm({ ...pedidoForm, chefId: e.target.value })}>
                            <option value="">Seleccione</option>
                            {combos.chefs.map(c => <option key={c.id} value={c.id}>{c.nombre}</option>)}
                        </select>
                    </div>

                    {/* Mozo */}
                    <div>
                        <label style={labelStyle}>Mozo</label>
                        <select style={inputStyle} value={pedidoForm.meseroId}
                                onChange={e => setPedidoForm({ ...pedidoForm, meseroId: e.target.value })}>
                            <option value="">Seleccione</option>
                            {combos.mozos.map(m => <option key={m.id} value={m.id}>{m.nombre}</option>)}
                        </select>
                    </div>

                    {/* Mesa */}
                    <div>
                        <label style={labelStyle}>Mesa Nº</label>
                        <input type="number" min="1" placeholder="Ej: 3" style={inputStyle}
                               value={pedidoForm.numeroMesa}
                               onChange={e => setPedidoForm({ ...pedidoForm, numeroMesa: e.target.value })} />
                    </div>

                    {/* Plato */}
                    <div>
                        <label style={labelStyle}>Plato / Postre</label>
                        <select style={inputStyle} value={pedidoForm.platoId}
                                onChange={e => setPedidoForm({ ...pedidoForm, platoId: e.target.value })}>
                            <option value="">Seleccione</option>
                            {platos.map(a => (
                                <option key={a.id} value={a.id}>{a.nombre} (${a.precio})</option>
                            ))}
                        </select>
                    </div>

                    {/* Bebida (opcional) */}
                    <div>
                        <label style={labelStyle}>Bebida <span style={{ fontWeight: 'normal', color: '#94a3b8' }}>(opcional)</span></label>
                        <select style={inputStyle} value={pedidoForm.bebidaId}
                                onChange={e => setPedidoForm({ ...pedidoForm, bebidaId: e.target.value })}>
                            <option value="">Sin bebida</option>
                            {bebidas.map(b => (
                                <option key={b.id} value={b.id}>{b.nombre} (${b.precio})</option>
                            ))}
                        </select>
                    </div>

                    {/* Botón Agregar */}
                    <div style={{ display: 'flex', alignItems: 'flex-end' }}>
                        <button onClick={agregarPedido} style={{
                            width: '100%', backgroundColor: '#22c55e', color: 'white',
                            border: 'none', padding: '10px', cursor: 'pointer',
                            fontWeight: 'bold', borderRadius: '4px',
                            display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '6px'
                        }}>
                            <Plus size={18} /> AGREGAR
                        </button>
                    </div>

                </div>
            </fieldset>

            {/* ── Tabla de pendientes (no guardados aún) ── */}
            <fieldset style={{ border: '1px solid #fbbf24', padding: '10px 16px', marginBottom: '24px', borderRadius: '6px' }}>
                <legend style={{ fontWeight: 'bold', padding: '0 8px', color: '#92400e' }}>
                    Pendientes de confirmar ({pendientes.length})
                </legend>

                {pendientes.length === 0 ? (
                    <p style={{ color: '#94a3b8', textAlign: 'center', margin: '12px 0' }}>
                        Ningún pedido agregado todavía.
                    </p>
                ) : (
                    <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                        <thead>
                        <tr style={{ backgroundColor: '#fef9c3' }}>
                            {['#', 'Chef', 'Mozo', 'Mesa', 'Plato', 'Bebida', 'Total', 'Quitar'].map(h => (
                                <th key={h} style={thStyle}>{h}</th>
                            ))}
                        </tr>
                        </thead>
                        <tbody>
                        {pendientes.map((p, i) => (
                            <tr key={p._tmpId}>
                                <td style={tdStyle}>{i + 1}</td>
                                <td style={tdStyle}>{nombreChef(p.chefId)}</td>
                                <td style={tdStyle}>{nombreMozo(p.meseroId)}</td>
                                <td style={tdStyle}>{p.numeroMesa}</td>
                                <td style={tdStyle}>{nombreAlimento(p.platoId)}</td>
                                <td style={tdStyle}>{p.bebidaId ? nombreAlimento(p.bebidaId) : '—'}</td>
                                <td style={{ ...tdStyle, color: '#16a34a', fontWeight: 'bold' }}>${p.total.toFixed(2)}</td>
                                <td style={tdStyle}>
                                    <button onClick={() => quitarPendiente(p._tmpId)} style={btnRojo}>
                                        <Trash2 size={15} />
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                )}

                {pendientes.length > 0 && (
                    <div style={{ textAlign: 'right', marginTop: '12px' }}>
                        <button onClick={confirmarPedidos} disabled={cargando} style={{
                            backgroundColor: cargando ? '#94a3b8' : '#3b82f6',
                            color: 'white', border: 'none', padding: '10px 24px',
                            fontWeight: 'bold', borderRadius: '4px', cursor: cargando ? 'not-allowed' : 'pointer'
                        }}>
                            {cargando ? 'Guardando...' : `💾 CONFIRMAR Y GUARDAR (${pendientes.length})`}
                        </button>
                    </div>
                )}
            </fieldset>

            {/* ── Tabla de pedidos confirmados (en BD) ── */}
            <fieldset style={{ border: '1px solid #86efac', padding: '10px 16px', borderRadius: '6px' }}>
                <legend style={{ fontWeight: 'bold', padding: '0 8px', color: '#166534' }}>
                    Pedidos en curso — guardados en BD ({confirmados.length})
                </legend>

                {confirmados.length === 0 ? (
                    <p style={{ color: '#94a3b8', textAlign: 'center', margin: '12px 0' }}>
                        No hay pedidos activos en la base de datos.
                    </p>
                ) : (
                    <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                        <thead>
                        <tr style={{ backgroundColor: '#dcfce7' }}>
                            {['ID', 'Chef', 'Mozo', 'Mesa', 'Plato', 'Bebida', 'Entregar'].map(h => (
                                <th key={h} style={thStyle}>{h}</th>
                            ))}
                        </tr>
                        </thead>
                        <tbody>
                        {confirmados.map(p => (
                            <tr key={p.id}>
                                <td style={tdStyle}>{p.id}</td>
                                <td style={tdStyle}>{p.chefNombre}</td>
                                <td style={tdStyle}>{p.meseroNombre}</td>
                                <td style={tdStyle}>{p.numeroMesa}</td>
                                <td style={tdStyle}>{p.platoNombre}</td>
                                <td style={tdStyle}>{p.bebidaNombre ?? '—'}</td>
                                <td style={tdStyle}>
                                    <button onClick={() => entregarPedido(p.id)} style={btnVerde}>
                                        ENTREGAR ✓
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                )}
            </fieldset>

            {/* Botones inferiores */}
            <div style={{ display: 'flex', justifyContent: 'center', gap: '20px', marginTop: '30px' }}>
                <button onClick={() => navigate('/')} style={{
                    display: 'flex', alignItems: 'center', gap: '8px',
                    backgroundColor: '#3b82f6', color: 'white',
                    padding: '10px 20px', border: 'none', borderRadius: '4px',
                    fontWeight: 'bold', cursor: 'pointer'
                }}>
                    <ArrowLeft size={16} /> Volver
                </button>
                <button onClick={() => alert('Sesión finalizada')} style={{
                    display: 'flex', alignItems: 'center', gap: '8px',
                    backgroundColor: '#ef4444', color: 'white',
                    padding: '10px 20px', border: 'none', borderRadius: '4px',
                    fontWeight: 'bold', cursor: 'pointer'
                }}>
                    <LogOut size={16} /> Salir
                </button>
            </div>

        </div>
    );
}

// ── Estilos compartidos ──────────────────────────────────────────────────────
const inputStyle = {
    width: '100%', padding: '9px 12px', border: '1px solid #cbd5e1',
    borderRadius: '4px', backgroundColor: '#ffffff', color: '#0f172a',
    boxSizing: 'border-box', outline: 'none', fontSize: '14px'
};

const labelStyle = {
    fontWeight: 'bold', marginBottom: '6px', display: 'block', color: '#374151', fontSize: '13px'
};

const thStyle = {
    border: '1px solid #cbd5e1', padding: '9px 10px',
    fontWeight: 'bold', textAlign: 'center', fontSize: '13px'
};

const tdStyle = {
    border: '1px solid #cbd5e1', padding: '9px 10px', textAlign: 'center', fontSize: '13px'
};

const btnRojo = {
    backgroundColor: '#ef4444', border: 'none', color: 'white',
    padding: '6px 10px', cursor: 'pointer', borderRadius: '4px',
    display: 'inline-flex', alignItems: 'center'
};

const btnVerde = {
    backgroundColor: '#22c55e', border: 'none', color: 'white',
    padding: '6px 14px', cursor: 'pointer', fontWeight: 'bold',
    borderRadius: '4px', fontSize: '12px'
};