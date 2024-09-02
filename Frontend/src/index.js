/* istanbul ignore file */ 

import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter, } from 'react-router-dom';
import { Provider } from 'react-redux';
import { store } from './Redux/Store';
import {QueryClient,QueryClientProvider } from '@tanstack/react-query';

const queryClient = new QueryClient();

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
    <Provider store={store}>
    <QueryClientProvider client = {queryClient}>
      <App />
      </QueryClientProvider>
    </Provider>
    </BrowserRouter>
  </React.StrictMode>
);

reportWebVitals();
