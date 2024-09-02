/* istanbul ignore file */ 

import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton,
  Button,
  Typography,
  TextField,
} from '@mui/material';
import { Delete as DeleteIcon, Edit as EditIcon } from '@mui/icons-material';
import { fetchAllProducts, deleteProduct, updateStockQuantity } from '../../../Redux/Product/Action';
import { useNavigate } from 'react-router-dom';

const ProductTable = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { products, loading, error } = useSelector(state => state.customersProduct);
  const [editingProductItemId, setEditingProductItemId] = useState(null);
  const [updatedStock, setUpdatedStock] = useState({});
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    dispatch(fetchAllProducts());
  }, [dispatch]);

  const handleDelete = (productItemId) => {
    dispatch(deleteProduct(productItemId));
  };

  const handleAddProduct = () => {
    navigate('/admin/product/create');
  };

  const handleEdit = (productItemId, quantityInStock) => {
    setEditingProductItemId(productItemId);
    setUpdatedStock({ [productItemId]: quantityInStock });
  };

  const handleStockChange = (e, productItemId) => {
    setUpdatedStock({
      ...updatedStock,
      [productItemId]: e.target.value,
    });
  };

  const handleUpdateStock = (productItemId) => {
    const updatedQuantity = parseInt(updatedStock[productItemId], 10);
    if (isNaN(updatedQuantity)) {
      alert('Please enter a valid quantity.');
      return;
    }
    if (updatedQuantity === 0) {
      alert('Quantity is 0! Please refill the product.');
      return;
    }

    dispatch(updateStockQuantity(productItemId, updatedQuantity));
    setEditingProductItemId(null);
  };

  const handleSearchChange = (e) => {
    setSearchTerm(e.target.value);
  };

  const filteredProducts = products.filter(product =>
    product.prodName.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <TableContainer component={Paper}>
      <Typography variant="h5" className="text-center mb-4">
        Product List
      </Typography>
      <Button variant="contained" color="primary" onClick={handleAddProduct} sx={{ mb: 2 }}>
        Add Product
      </Button>
      <TextField
        label="Search by Product Name"
        value={searchTerm}
        onChange={handleSearchChange}
        variant="outlined"
        fullWidth
        sx={{ mb: 2 }}
      />
      {loading ? (
        <Typography variant="h6" className="text-center">
          Loading...
        </Typography>
      ) : error ? (
        <Typography variant="h6" className="text-center" color="error">
          {error}
        </Typography>
      ) : (
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Product Name</TableCell>
              <TableCell>Brand</TableCell>
              <TableCell>Category</TableCell>
              <TableCell>Price</TableCell>
              <TableCell>Sale Price</TableCell>
              <TableCell>Quantity</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredProducts.map((product) => (
              <TableRow key={product.productItemId}>
                <TableCell>{product.productItemId}</TableCell>
                <TableCell>{product.prodName}</TableCell>
                <TableCell>{product.brand}</TableCell>
                <TableCell>{product.categoryName}</TableCell>
                <TableCell>{product.price}</TableCell>
                <TableCell>{product.salePrice}</TableCell>
                <TableCell>
                  {editingProductItemId === product.productItemId ? (
                    <TextField
                      value={updatedStock[product.productItemId] || ''}
                      onChange={(e) => handleStockChange(e, product.productItemId)}
                      type="number"
                    />
                  ) : (
                    product.quantityInStock
                  )}
                </TableCell>
                <TableCell>
                  <div style={{ display: 'flex', alignItems: 'center' }}>
                    {editingProductItemId === product.productItemId ? (
                      <Button onClick={() => handleUpdateStock(product.productItemId)}>Save</Button>
                    ) : (
                      <IconButton onClick={() => handleEdit(product.productItemId, product.quantityInStock)}>
                        <EditIcon />
                      </IconButton>
                    )}
                    <IconButton onClick={() => handleDelete(product.productItemId)}>
                      <DeleteIcon />
                    </IconButton>
                  </div>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}
    </TableContainer>
  );
};

export default ProductTable;
