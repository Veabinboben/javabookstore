import axios, { type AxiosInstance } from 'axios';

const apiBase = import.meta.env.VITE_API_BASE_URL;

export const createHttp = (baseURL = apiBase): AxiosInstance => {
    const http = axios.create({   
            baseURL, 
            timeout: 10000, 
        }
    );
    //   http.interceptors.request.use(cfg => {
    //     const token = localStorage.getItem('token');
    //     if (token && cfg.headers) cfg.headers.Authorization = `Bearer ${token}`;
    //     return cfg;
    //   });
    return http;
};