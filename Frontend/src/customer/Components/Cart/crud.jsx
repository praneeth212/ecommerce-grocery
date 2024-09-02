import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';
import DeleteIcon from '@mui/icons-material/Delete';
import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import Box from '@mui/material/Box';

const Crud = () => {
    const cartItems = useSelector((state) => state.cart.cartItems);
    const totalItems = useSelector((state) => state.cart.totalItems);
    const totalPrice = useSelector((state) => state.cart.totalPrice);
    const totalSalePrice = useSelector((state) => state.cart.totalSalePrice);
    const notification = useSelector((state) => state.cart.notification);
    const dispatch = useDispatch();
    const navigate=useNavigate();
    const [error, setError] = useState('');

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = () => {
        axios.get('http://localhost:8084/cart/list')
            .then(response => {
                const data = response.data;
                console.log('Cart items:', data);
                dispatch({
                    type: 'FETCH_CART_SUCCESS',
                    payload: {
                        cartItems: data,
                        totalItems: data?.reduce((acc, item) => acc + item.quantity, 0),
                        totalPrice: data?.reduce((acc, item) => acc + (item.price * item.quantity), 0),
                        totalSalePrice: data?.reduce((acc, item) => acc + (item.discountPrice * item.quantity), 0),
                    }
                });
            })
            .catch(error => {
                console.error('Error fetching cart items:', error);
                setError('Error fetching cart items: ' + (error.response?.data?.message || 'Unknown error'));
            });
    };

    const handleIncrement = (index) => {
       // console.log(index)
        const item = cartItems[index];
        console.log('item is',item.productItem)
        const productItemId = item.productItem?.id;
        console.log(productItemId)
        if (productItemId) {
            const payload = { quantity: item.quantity + 1 };
            axios.put(`http://localhost:8084/cart/update/${productItemId}`, payload)
                .then(response => {
                    fetchData();
                    setError("");
                })
                .catch(error => {
                    console.error('Failed to update cart item quantity:', error);
                    setError('Failed to update cart item quantity: ' + (error.response?.data?.message || 'product is out of stock'));
                });
        } else {
            setError('Product item ID is undefined');
        }
    };

    const handleDecrement = (index) => {
        const item = cartItems[index];
        const productItemId = item.productItem.id;
        if (productItemId) {
            if (item.quantity > 1) {
                const payload = { quantity: item.quantity - 1 };
                axios.put(`http://localhost:8084/cart/update/${productItemId}`, payload)
                    .then(response => {
                        fetchData();
                        setError("");
                    })
                    .catch(error => {
                        console.error('Failed to update cart item quantity:', error);
                        setError('Failed to update cart item quantity: ' + (error.response?.data?.message || 'Unknown error'));
                    });
            } else {
                setError('Quantity cannot be less than 1');
            }
        } else {
            setError('Product item ID is undefined');
        }
    };

    const handleDelete = (index) => {
        const item = cartItems[index];
        const productItemId = item.productItem.id;
        if (productItemId) {
            axios.delete(`http://localhost:8084/cart/deleteproduct/${productItemId}`)
                .then(response => {
                    fetchData();
                })
                .catch(error => {
                    console.error('Error deleting item:', error);
                    setError('Failed to delete cart item: ' + (error.response?.data?.message || 'Unknown error'));
                });
        } else {
            setError('Product item ID is undefined');
        }
    };
    const handleCheckout = () => {
        if (cartItems.length === 0) {
            alert("Your cart is empty!");
        } else {
            navigate('/checkout');
        }
    };
    return (
        <div className="min-h-screen bg-gray-100 flex flex-col">

            {/* Cart Items */}
            <Container maxWidth="lg" className="flex-grow py-6">
                {/* {notification && <Alert severity="success">{notification}</Alert>} */}
                {/* {error && <Alert severity="error">{error}</Alert>} */}
                {error && (
                    <Box mb={2} p={2} bgcolor="error.main" color="error.contrastText" borderRadius="4px">
                        <Typography>{error}</Typography>
                    </Box>
                )}
                <Grid container spacing={4}>
                    <Grid item xs={12} md={8}>
                        {cartItems.map((item, index) => (
                            <Paper key={item.cartItemId} className="p-4 mb-4">
                                <Grid container spacing={2} alignItems="center">
                                    <Grid item xs={3}>
                                        <img src={`http://localhost:8082${item.productImageUrl}`}alt={item.prodName} className="w-full h-24 object-cover rounded" />
                                    </Grid>
                                    <Grid item xs={6}>
                                        {console.log('item in crud',item)}
                                        <Typography variant="h6">{item.prodName}</Typography>
                                        <Typography variant="body2" color="textSecondary">{item.unitId} {item.unitname}</Typography>
                                        <Typography variant="body1" color="textSecondary" className="line-through">&#8377;{(item.price * item.quantity).toFixed(2)}</Typography>
                                        <Typography variant="h6" color="primary">Discount Price: &#8377;{(item.discountPrice * item.quantity).toFixed(2)}</Typography>
                                    </Grid>
                                    <Grid item xs={3}>
                                        <Box display="flex" alignItems="center">
                                            <IconButton color="secondary" onClick={() => handleDecrement(index)}>
                                                <RemoveIcon />
                                            </IconButton>
                                            <Typography variant="body1">{item.quantity}</Typography>
                                            <IconButton color="primary" onClick={() => handleIncrement(index)}>
                                                <AddIcon />
                                            </IconButton>
                                            <IconButton color="error" onClick={() => handleDelete(index)}>
                                                <DeleteIcon />
                                            </IconButton>
                                        </Box>
                                    </Grid>
                                </Grid>
                            </Paper>
                        ))}
                    </Grid>
                    <Grid item xs={12} md={4}>
                        <Paper className="p-4">
                            <Typography variant="h5" gutterBottom> Summary</Typography>
                            <Divider />
                            <Box display="flex" justifyContent="space-between" my={2}>
                                <Typography variant="body1">Total Items:</Typography>
                                <Typography variant="body1">{totalItems}</Typography>
                            </Box>
                            <Box display="flex" justifyContent="space-between" my={2}>
                                <Typography variant="body1">Total Price:</Typography>
                                <Typography variant="body1" className="line-through">&#8377;{totalPrice.toFixed(2)}</Typography>
                            </Box>
                            <Box display="flex" justifyContent="space-between" my={2}>
                                <Typography variant="body1">Total Discount Price:</Typography>
                                <Typography variant="body1" color="primary">&#8377;{totalSalePrice.toFixed(2)}</Typography>
                            </Box>
                            <Link to="/checkout">
                                <Button variant="contained" color="primary" fullWidth onClick={handleCheckout}>
                                    Checkout
                                </Button>
                            </Link>
                        </Paper>
                    </Grid>
                </Grid>
            </Container>
        </div>
    );
};

export default Crud;