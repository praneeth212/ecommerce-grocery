/* istanbul ignore file */ 
import axios from 'axios';


const api = axios.create({
  
});

const token = localStorage.getItem('jwt');

api.defaults.headers.common['Authorization'] = `Bearer ${token}`;

api.defaults.headers.post['Content-Type'] = 'application/json';

export default api;