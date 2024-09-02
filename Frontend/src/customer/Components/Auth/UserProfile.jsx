import React, { useState, useEffect } from 'react';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { Button, TextField, Radio, FormControlLabel, IconButton, FormHelperText } from '@mui/material';
import { Delete, Edit, Token } from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { API_BASE_URL } from '../../../config/api';
import { logout } from '../../../Redux/Auth/Action';
import { useDispatch, useSelector } from 'react-redux';

const UserProfile = () => {
  const [user, setUser] = useState({
    firstName: '',
    lastName: '',
    email: '',
    mobile: '',
    addresses: [],
  });
  const [newAddress, setNewAddress] = useState({
    address_line1: '',
    address_line2: '',
    country: '',
    city: '',
    state: '',
    zipcode: '',
  });
  const [errors, setErrors] = useState({});
  const [isEditing, setIsEditing] = useState(false);
  const [editingAddressId, setEditingAddressId] = useState(null);
  const [showAddressForm, setShowAddressForm] = useState(false);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const auth = useSelector((state) => state.auth);



  useEffect(() => {
    const fetchProfile = async () => {
      const response = await fetch(`http://localhost:8085/api/users/profile`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem("jwt")}`,
        },
      });
      const data = await response.json();
      setUser(data);
    };

    const fetchAddresses = async () => {
      const response = await fetch(`http://localhost:8085/api/users/${auth.user.id}/addresses`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
        },
      });
      const data = await response.json();
      setUser((prevState) => ({ ...prevState, addresses: data }));
    };

    fetchProfile();
    fetchAddresses();
  }, [auth.user?.id]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const handleAddressChange = (e) => {
    const { name, value } = e.target;
    setNewAddress({ ...newAddress, [name]: value });
    validateField(name, value);
  };

  const validateField = (name, value) => {
    let error = '';
    switch (name) {
      case 'address_line1':
      case 'address_line2':
        if (value.length < 5) error = 'Address must be at least 5 characters';
        if (value.length > 100) error = 'Address cannot exceed 100 characters';
        break;
      case 'city':
      case 'state':
      case 'country':
        if (value.length < 2) error = `${name.charAt(0).toUpperCase() + name.slice(1)} must be at least 2 characters`;
        if (value.length > 50) error = `${name.charAt(0).toUpperCase() + name.slice(1)} cannot exceed 50 characters`;
        if (!/^[a-zA-Z\s]+$/.test(value)) error = `${name.charAt(0).toUpperCase() + name.slice(1)} must contain only letters and spaces`;
        break;
      case 'zipcode':
        if (!/^\d+$/.test(value)) error = 'Zipcode must contain only numbers';
        if (value.length !== 6) error = 'Zipcode must be 5 or 9 characters';
        break;
      default:
        break;
    }
    setErrors((prevErrors) => ({ ...prevErrors, [name]: error }));
  };

  const handleSave = async () => {
    const response = await fetch(`http://localhost:8085/api/users/updateprofile`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
      },
      body: JSON.stringify(user),
    });

    if (response.ok) {
      const updatedUser = await response.json();
      setUser(updatedUser);
      setIsEditing(false);
    } else {
      // error
    }
  };

  const handleAddAddress = async () => {
    const response = await fetch(`http://localhost:8085/api/users/${user.id}/addresses`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
      },
      body: JSON.stringify(newAddress),
    });

    if (response.ok) {
      const addedAddress = await response.json();
      setUser({ ...user, addresses: [...user.addresses, addedAddress] });
      setNewAddress({
        address_line1: '',
        address_line2: '',
        country: '',
        city: '',
        state: '',
        zipcode: '',
      });
      setShowAddressForm(false);
    } else {
      // error
    }
  };

  const handleUpdateAddress = async (addressId) => {
    const response = await fetch(`http://localhost:8085/api/users/${user.id}/addresses/${addressId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
      },
      body: JSON.stringify(newAddress),
    });

    if (response.ok) {
      const updatedAddress = await response.json();
      setUser((prevState) => ({
        ...prevState,
        addresses: prevState.addresses.map((address) =>
          address.id === addressId ? updatedAddress : address
        ),
      }));
      setNewAddress({
        address_line1: '',
        address_line2: '',
        country: '',
        city: '',
        state: '',
        zipcode: '',
      });
      setEditingAddressId(null);
      setShowAddressForm(false);
    } else {
      // error
    }
  };

  const handleDeleteAddress = async (addressId) => {
    const response = await fetch(`http://localhost:8085/api/users/${user.id}/addresses/${addressId}`, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
      },
    });

    if (response.ok) {
      let updatedAddresses = user.addresses.filter((address) => address.id !== addressId);

      // Check if the deleted address was the default address
      const deletedAddress = user.addresses.find((address) => address.id === addressId);
      if (deletedAddress && deletedAddress.default_address) {
        // Set another address as the default
        if (updatedAddresses.length > 0) {
          const newDefaultAddressId = updatedAddresses[0].id;
          await setNewDefaultAddress(newDefaultAddressId);
          updatedAddresses = updatedAddresses.map((address) =>
            address.id === newDefaultAddressId ? { ...address, default_address: true } : address
          );
        }
      }

      setUser((prevState) => ({
        ...prevState,
        addresses: updatedAddresses,
      }));
    } else {
      // error
    }
  };

  const setNewDefaultAddress = async (addressId) => {
    const response = await fetch(`http://localhost:8085/api/users/${user.id}/addresses/${addressId}/default`, {
      method: 'PUT',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
      },
    });

    if (response.ok) {
      const updatedAddresses = await response.json();
      setUser((prevState) => ({ ...prevState, addresses: updatedAddresses }));
    } else {
      // error
    }
  };

  const handleEditAddress = (address) => {
    setNewAddress(address);
    setEditingAddressId(address.id);
    setShowAddressForm(true);
  };

  const handleSetDefaultAddress = async (addressId) => {
    const response = await fetch(`http://localhost:8085/api/users/${user.id}/addresses/${addressId}/default`, {
      method: 'PUT',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
      },
    });

    if (response.ok) {
      const updatedAddresses = await response.json();
      setUser((prevState) => ({ ...prevState, addresses: updatedAddresses }));
    } else {
      // error
    }
  };

  const handleLogout = () => {
    dispatch(logout());
    navigate("/");
  };

  const isAddressValid = () => {
    const { address_line1, address_line2, country, city, state, zipcode } = newAddress;
    return address_line1 && address_line2 && country && city && state && zipcode && Object.values(errors).every((error) => !error);
  };

  return (
    <div className='min-h-screen flex flex-col items-center bg-gray-100 py-10'>
      <div className='bg-white shadow-md rounded-lg p-8 w-11/12 max-w-5xl'>
        <div className='flex items-center justify-center mb-6'>
          <AccountCircleIcon sx={{ fontSize: '6rem' }} />
        </div>
        <h1 className='text-3xl font-semibold text-gray-800 text-center mb-4'>
          <span className='font-bold'>
            {`${user.firstName} ${user.lastName}`.replace(/\b\w/g, (c) => c.toUpperCase())}
          </span>
        </h1>
        <div className='flex space-x-4'>
          <div className='w-1/2 space-y-4'>
            {isEditing ? (
              <>
                <TextField
                  fullWidth
                  label='First Name'
                  name='firstName'
                  value={user.firstName}
                  onChange={handleInputChange}
                  sx={{ marginBottom: '0.5rem' }}
                />
                <TextField
                  fullWidth
                  label='Last Name'
                  name='lastName'
                  value={user.lastName}
                  onChange={handleInputChange}
                  sx={{ marginBottom: '0.5rem' }}
                />
                <TextField
                  fullWidth
                  label='Mobile'
                  name='mobile'
                  value={user.mobile}
                  onChange={handleInputChange}
                  sx={{ marginBottom: '0.5rem' }}
                />
                <Button onClick={handleSave} variant='contained' sx={{ margin: '1rem 0' }} fullWidth>
                  Save
                </Button>
              </>
            ) : (
              <>
                <p className='text-gray-800'><strong>First Name:</strong> {user.firstName}</p>
                <p className='text-gray-800'><strong>Last Name:</strong> {user.lastName}</p>
                <p className='text-gray-800'><strong>Email:</strong> {user.email}</p>
                <p className='text-gray-800'><strong>Mobile:</strong> {user.mobile}</p>
                <Button onClick={() => setIsEditing(true)} variant='contained' sx={{ margin: '1rem 0' }} fullWidth>
                  Edit
                </Button>
              </>
            )}
            <h2 className='text-2xl font-semibold text-gray-800'>Addresses</h2>
            {user.addresses.map((address) => (
              <div key={address.id} className='border p-4 rounded mb-4 flex justify-between items-center'>
                <div>
                  <FormControlLabel
                    control={
                      <Radio
                        checked={address.default_address}
                        onChange={() => handleSetDefaultAddress(address.id)}
                        disabled={address.default_address}
                      />
                    }
                    label={address.default_address ? 'Default Address' : ''}
                  />
                  <p>{address.address_line1}</p>
                  <p>{address.address_line2}</p>
                  <p>{address.city}, {address.state} {address.zipcode}</p>
                  <p>{address.country}</p>
                </div>
                <div>
                  <IconButton onClick={() => handleEditAddress(address)}>
                    <Edit />
                  </IconButton>
                  <IconButton onClick={() => handleDeleteAddress(address.id)}>
                    <Delete />
                  </IconButton>
                </div>
              </div>
            ))}
            {user.addresses.length < 3 && (
              <Button onClick={() => setShowAddressForm(true)} variant='contained' sx={{ margin: '1rem 0' }} fullWidth>
                Add Address
              </Button>
            )}
          </div>
          {showAddressForm && (
            <div className='w-1/2 space-y-4'>
              <h2 className='text-2xl font-semibold text-gray-800'>{editingAddressId ? 'Edit Address' : 'Add New Address'}</h2>
              <TextField
                fullWidth
                label='Address Line 1'
                name='address_line1'
                value={newAddress.address_line1}
                onChange={handleAddressChange}
                sx={{ marginBottom: '0.5rem' }}
                error={!!errors.address_line1}
                helperText={errors.address_line1}
              />
              <TextField
                fullWidth
                label='Address Line 2'
                name='address_line2'
                value={newAddress.address_line2}
                onChange={handleAddressChange}
                sx={{ marginBottom: '0.5rem' }}
                error={!!errors.address_line2}
                helperText={errors.address_line2}
              />
              <TextField
                fullWidth
                label='City'
                name='city'
                value={newAddress.city}
                onChange={handleAddressChange}
                sx={{ marginBottom: '0.5rem' }}
                error={!!errors.city}
                helperText={errors.city}
              />
              <TextField
                fullWidth
                label='State'
                name='state'
                value={newAddress.state}
                onChange={handleAddressChange}
                sx={{ marginBottom: '0.5rem' }}
                error={!!errors.state}
                helperText={errors.state}
              />
              <TextField
                fullWidth
                label='Country'
                name='country'
                value={newAddress.country}
                onChange={handleAddressChange}
                sx={{ marginBottom: '0.5rem' }}
                error={!!errors.country}
                helperText={errors.country}
              />
              <TextField
                fullWidth
                label='Zipcode'
                name='zipcode'
                value={newAddress.zipcode}
                onChange={handleAddressChange}
                sx={{ marginBottom: '0.5rem' }}
                error={!!errors.zipcode}
                helperText={errors.zipcode}
              />
              <Button 
                onClick={() => editingAddressId ? handleUpdateAddress(editingAddressId) : handleAddAddress()} 
                variant='contained' 
                sx={{ margin: '1rem 0' }} 
                fullWidth 
                disabled={!isAddressValid()}
              >
                {editingAddressId ? 'Update Address' : 'Add Address'}
              </Button>
            </div>
          )}
        </div>
        <Button onClick={handleLogout} variant='contained' sx={{ margin: '1rem 0' }} fullWidth>
          Logout
        </Button>
      </div>
    </div>
  );
};

export default UserProfile;
