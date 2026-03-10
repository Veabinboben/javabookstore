import axios, { type AxiosInstance } from 'axios';

export const createHttp = (baseURL = 'http://localhost:8080/'): AxiosInstance => {
    // add config here
    const http = axios.create({   
            baseURL, 
            timeout: 10000, 
            headers: { 'Content-Type': 'application/json' } 
        }
    );
    //   http.interceptors.request.use(cfg => {
    //     const token = localStorage.getItem('token');
    //     if (token && cfg.headers) cfg.headers.Authorization = `Bearer ${token}`;
    //     return cfg;
    //   });
    return http;
};