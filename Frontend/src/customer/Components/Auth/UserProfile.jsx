import React, { useState } from 'react';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { Button, TextField } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { API_BASE_URL } from '../../../config/api';
import { logout } from '../../../Redux/Auth/Action';
import { useDispatch } from 'react-redux';

const UserProfile = () => {
  const [user, setUser] = useState({
    firstName: '',
    lastName: '',
    email: '',
    mobile: '',
  });
  const [isEditing, setIsEditing] = useState(false);
  const navigate = useNavigate();
  const dispatch = useDispatch();

  // Fetch user profile on component mount
  React.useEffect(() => {
    const fetchProfile = async () => {
      const response = await fetch(`${API_BASE_URL}/api/users/profile`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
        },
      });
      const data = await response.json();
      setUser(data);
    };

    fetchProfile();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const handleSave = async () => {
    const response = await fetch(`${API_BASE_URL}/api/users/updateprofile`, {
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
      // Handle error
    }
  };

  const handleLogout = () => {
    dispatch(logout());
    navigate("/");
  };

  return (
    <div className='min-h-screen flex flex-col items-center bg-gray-100 py-10'>
      <div className='bg-white shadow-md rounded-lg p-8 w-full max-w-md'>
        <div className='flex items-center justify-center mb-6'>
          <AccountCircleIcon sx={{ fontSize: '6rem' }} />
        </div>
        <h1 className='text-3xl font-semibold text-gray-800 text-center mb-4'>
          <span className='font-bold'>
            {`${user.firstName} ${user.lastName}`.replace(/\b\w/g, (c) => c.toUpperCase())}
          </span>
        </h1>
        <div className='space-y-4'>
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
                label='Email'
                name='email'
                value={user.email}
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
        </div>
        <Button onClick={handleLogout} variant='contained' sx={{ margin: '1rem 0' }} fullWidth>
          Logout
        </Button>
      </div>
    </div>
  );
};

export default UserProfile;
