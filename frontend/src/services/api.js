import axios from "axios";

const api = axios.create({

    baseURL: "/"

});

api.interceptors.request.use(config => {

    const token = localStorage.getItem("token");

    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    const prefeituraSelecionada =
        localStorage.getItem("prefeituraSelecionada");

    if (prefeituraSelecionada) {
        config.headers["X-Prefeitura-Id"] =
            prefeituraSelecionada;
    }

    return config;

});

export default api;
