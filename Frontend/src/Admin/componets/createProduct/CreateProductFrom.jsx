/* istanbul ignore file */ 

import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { TextField, Button, Grid, Box, Typography, IconButton, Snackbar, Alert } from '@mui/material';
import { Add as AddIcon, Remove as RemoveIcon } from '@mui/icons-material';
import { styled } from '@mui/material/styles';
import { addProduct } from '../../../Redux/Product/Action';
import { useNavigate } from 'react-router-dom';

import './CreateProductForm.css';

const FormContainer = styled(Box)(({ theme }) => ({
    maxWidth: '800px',
    margin: '0 auto',
    padding: theme.spacing(4),
    backgroundColor: '##121019',
    boxShadow: theme.shadows[2],
    borderRadius: theme.shape.borderRadius,
}));

const CreateProductForm = () => {
    const [formState, setFormState] = useState({
        category: {
            name: '',
            description: '',
            parentCategory: {
                name: '',
                description: '',
            }
        },
        prodName: '',
        description: '',
        brand: '',
        productItems: [
            {
                price: '',
                salePrice: '',
                quantityInStock: '',
                unit: {
                    unitName: ''
                }
            }
        ]
    });

    const [categoryImageFile, setCategoryImageFile] = useState(null);
    const [productImageFile, setProductImageFile] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleChange = (e, path) => {
        const { name, value } = e.target;
        setFormState(prevState => {
            const newState = { ...prevState };
            if (path) {
                const pathParts = path.split('.');
                let obj = newState;
                pathParts.forEach((part, index) => {
                    if (index === pathParts.length - 1) {
                        obj[part] = value;
                    } else {
                        obj = obj[part];
                    }
                });
            } else {
                newState[name] = value;
            }
            return newState;
        });
    };

    const handleFileChange = (e, setFile) => {
        setFile(e.target.files[0]);
    };

    const handleProductItemChange = (e, index, unitField = false) => {
        const { name, value } = e.target;
        const updatedProductItems = formState.productItems.map((item, idx) =>
            idx === index ? {
                ...item,
                [unitField ? 'unit' : name]: unitField ? { ...item.unit, [name]: value } : value
            } : item
        );
        setFormState({ ...formState, productItems: updatedProductItems });
    };

    const addProductItem = () => {
        setFormState(prevState => ({
            ...prevState,
            productItems: [
                ...prevState.productItems,
                {
                    price: '',
                    salePrice: '',
                    quantityInStock: '',
                    unit: {
                        unitName: ''
                    }
                }
            ]
        }));
    };

    const removeProductItem = index => {
        setFormState(prevState => ({
            ...prevState,
            productItems: prevState.productItems.filter((_, idx) => idx !== index)
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append('product', JSON.stringify(formState));
        if (categoryImageFile) {
            formData.append('categoryImageFile', categoryImageFile);
        }
        if (productImageFile) {
            formData.append('productImageFile', productImageFile);
        }
        await dispatch(addProduct(formData));
        setSuccessMessage('Product Added Successfully');
        setFormState({
            category: {
                name: '',
                description: '',
                parentCategory: {
                    name: '',
                    description: '',
                }
            },
            prodName: '',
            description: '',
            brand: '',
            productItems: [
                {
                    price: '',
                    salePrice: '',
                    quantityInStock: '',
                    unit: {
                        unitName: ''
                    }
                }
            ]
        });
        setCategoryImageFile(null);
        setProductImageFile(null);
        setTimeout(() => {
            setSuccessMessage('');
            navigate('/admin/products'); // Redirect to product list or another page if needed
        }, 3000);
    };

    return (
        <div className="createProductContainer">
            <FormContainer>
                <Typography variant="h5" className="text-center mb-4">
                    Add Product
                </Typography>
                {successMessage && (
                    <Snackbar open={true} autoHideDuration={6000}>
                        <Alert severity="success" sx={{ width: '100%' }}>
                            {successMessage}
                        </Alert>
                    </Snackbar>
                )}
                <form onSubmit={handleSubmit}>
                    <Typography variant="h6" gutterBottom>
                        Category Details
                    </Typography>
                    <Grid container spacing={2} alignItems="flex-end">
                        <Grid item xs={12} sm={6}>
                            <TextField
                                label="Category Name"
                                name="name"
                                value={formState.category.name}
                                onChange={e => handleChange(e, 'category.name')}
                                required
                                variant="outlined"
                                fullWidth
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                label="Category Description"
                                name="description"
                                required
                                value={formState.category.description}
                                onChange={e => handleChange(e, 'category.description')}
                                variant="outlined"
                                fullWidth
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <Typography variant="body2">Upload Category Image</Typography>
                            <input
                                type="file"
                                onChange={e => handleFileChange(e, setCategoryImageFile)}
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                label="Parent Category Name"
                                name="name"
                                value={formState.category.parentCategory.name}
                                onChange={e => handleChange(e, 'category.parentCategory.name')}
                                required
                                variant="outlined"
                                fullWidth
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                label="Parent Category Description"
                                name="description"
                                value={formState.category.parentCategory.description}
                                onChange={e => handleChange(e, 'category.parentCategory.description')}
                                required
                                variant="outlined"
                                fullWidth
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <Typography variant="body2">Upload Parent Category Image</Typography>
                            <input
                                type="file"
                                onChange={e => handleFileChange(e, setCategoryImageFile)}
                            />
                        </Grid>
                    </Grid>

                    <Typography variant="h6" gutterBottom>
                        Product Details
                    </Typography>
                    <Grid container spacing={2} alignItems="flex-end">
                        <Grid item xs={12} sm={6}>
                            <TextField
                                label="Product Name"
                                name="prodName"
                                value={formState.prodName}
                                onChange={handleChange}
                                required
                                variant="outlined"
                                fullWidth
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                label="Brand"
                                name="brand"
                                value={formState.brand}
                                onChange={handleChange}
                                required
                                variant="outlined"
                                fullWidth
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                label="Description"
                                name="description"
                                value={formState.description}
                                onChange={handleChange}
                                required
                                multiline
                                rows={4}
                                variant="outlined"
                                fullWidth
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <Typography variant="body2">Upload Product Image</Typography>
                            <input
                                type="file"
                                onChange={e => handleFileChange(e, setProductImageFile)}
                                required
                            />
                        </Grid>
                    </Grid>

                    <Typography variant="h6" gutterBottom>
                        Product Items
                    </Typography>
                    {formState.productItems.map((item, index) => (
                        <Box key={index} mb={2}>
                            <Grid container spacing={2} alignItems="flex-end">
                                <Grid item xs={12} sm={3}>
                                    <TextField
                                        label="Price"
                                        name="price"
                                        value={item.price}
                                        onChange={e => handleProductItemChange(e, index)}
                                        required
                                        variant="outlined"
                                        fullWidth
                                        type="number"
                                    />
                                </Grid>
                                <Grid item xs={12} sm={3}>
                                    <TextField
                                        label="Sale Price"
                                        name="salePrice"
                                        value={item.salePrice}
                                        onChange={e => handleProductItemChange(e, index)}
                                        required
                                        variant="outlined"
                                        fullWidth
                                        type="number"
                                    />
                                </Grid>
                                <Grid item xs={12} sm={3}>
                                    <TextField
                                        label="Quantity in Stock"
                                        name="quantityInStock"
                                        value={item.quantityInStock}
                                        onChange={e => handleProductItemChange(e, index)}
                                        required
                                        variant="outlined"
                                        fullWidth
                                        type="number"
                                    />
                                </Grid>
                                <Grid item xs={12} sm={3}>
                                    <TextField
                                        label="Unit"
                                        name="unitName"
                                        value={item.unit.unitName}
                                        onChange={e => handleProductItemChange(e, index, true)}
                                        required
                                        variant="outlined"
                                        fullWidth
                                    />
                                </Grid>
                                <Grid item xs={12} sm={3}>
                                    <IconButton onClick={() => removeProductItem(index)}>
                                        <RemoveIcon />
                                    </IconButton>
                                    {index === formState.productItems.length - 1 && (
                                        <IconButton onClick={addProductItem}>
                                            <AddIcon />
                                        </IconButton>
                                    )}
                                </Grid>
                            </Grid>
                        </Box>
                    ))}

                    <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        fullWidth
                        sx={{ mt: 2 }}
                    >
                        Add Product
                    </Button>
                </form>
            </FormContainer>
        </div>
    );
};

export default CreateProductForm;
