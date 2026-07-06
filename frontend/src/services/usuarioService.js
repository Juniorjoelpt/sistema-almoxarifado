import api from "./api";

const URL = "/api/usuarios";

export async function listarUsuarios() {
    return await api.get(URL);
}

export async function buscarUsuario(id) {
    return await api.get(`${URL}/${id}`);
}

export async function salvarUsuario(usuario) {
    return await api.post(URL, usuario);
}

export async function atualizarUsuario(id, usuario) {
    return await api.put(`${URL}/${id}`, usuario);
}

export async function alterarStatus(id, ativo) {
    return await api.patch(
        `${URL}/${id}/ativo?ativo=${ativo}`
    );
}

export async function excluirUsuario(id) {
    return await api.delete(`${URL}/${id}`);
}