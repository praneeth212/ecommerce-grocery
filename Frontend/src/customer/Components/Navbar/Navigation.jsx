/* istanbul ignore file */ 
import React, { useState, useEffect, Fragment } from 'react';
import { Dialog, Tab, Transition } from '@headlessui/react';
import {
  MagnifyingGlassIcon,
  ShoppingCartIcon,
  XMarkIcon,
} from '@heroicons/react/24/outline';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Avatar, Button, Menu, MenuItem} from '@mui/material';
import { navigation } from '../../../config/navigationMenu';
import AuthModal from '../Auth/AuthModal';
import { useDispatch, useSelector } from 'react-redux';
import { getUser, logout } from '../../../Redux/Auth/Action';
import Badge from '@mui/material/Badge';

function classNames(...classes) {
  return classes.filter(Boolean).join(' ');
}

export default function Navigation() {
  const [open, setOpen] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { auth, cart } = useSelector((store) => store);
  const [openAuthModal, setOpenAuthModal] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  const openUserMenu = Boolean(anchorEl);
  const jwt = localStorage.getItem('jwt');
  const location = useLocation();
  const totalItems = useSelector((state) => state.cart.totalItems);

  useEffect(() => {
    if (jwt) {
      dispatch(getUser(jwt));
    }
  }, [jwt]);

  const handleUserClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleCloseUserMenu = () => {
    setAnchorEl(null);
  };

  const handleOpen = () => {
    setOpenAuthModal(true);
    navigate('/register');
  };
  const handleClose = () => {
    setOpenAuthModal(false);
    navigate('/');
  };

  useEffect(() => {
    if (auth.user) {
      handleClose();
    }
    if (
      location.pathname === '/login' ||
      location.pathname === '/register' ||
      location.pathname === '/forgot' ||
      location.pathname === '/account/reset-password-change'
    ) {
      navigate(-1);
    }
  }, [auth.user]);

  const handleLogout = () => {
    handleCloseUserMenu();
    dispatch(logout());
    navigate('/');
  };

  const handleMyOrderClick = () => {
    handleCloseUserMenu();
    auth.user?.role === 'ROLE_ADMIN'
      ? navigate('/admin')
      : navigate('/account/order');
  };

  const handleMyOrderProfileClick = () => {
    handleCloseUserMenu();
    auth.user?.role === 'ROLE_CUSTOMER' ? navigate('/userprofile') : navigate('/admin');
  };

  const handleSearch = (e) => {
    e.preventDefault();
    navigate(`/search/${searchQuery}`);
  };

  return (
    <div className="bg-white pb-10">
      <Transition.Root show={open} as={Fragment}>
        <Dialog as="div" className="relative z-40 lg:hidden" onClose={setOpen}>
          <Transition.Child
            as={Fragment}
            enter="transition-opacity ease-linear duration-300"
            enterFrom="opacity-0"
            enterTo="opacity-100"
            leave="transition-opacity ease-linear duration-300"
            leaveFrom="opacity-100"
            leaveTo="opacity-0"
          >
            <div className="fixed inset-0 bg-black bg-opacity-25" />
          </Transition.Child>

          <div className="fixed inset-0 z-40 flex">
            <Transition.Child
              as={Fragment}
              enter="transition ease-in-out duration-300 transform"
              enterFrom="-translate-x-full"
              enterTo="translate-x-0"
              leave="transition ease-in-out duration-300 transform"
              leaveFrom="translate-x-0"
              leaveTo="-translate-x-full"
            >
              <Dialog.Panel className="relative flex w-full max-w-xs flex-col overflow-y-auto bg-white pb-12 shadow-xl">
                <div className="flex px-4 pb-2 pt-5">
                  <button
                    type="button"
                    className="-m-2 inline-flex items-center justify-center rounded-md p-2 text-gray-400"
                    onClick={() => setOpen(false)}
                  >
                    <span className="sr-only">Close menu</span>
                    <XMarkIcon className="h-6 w-6" aria-hidden="true" />
                  </button>
                </div>

                <Tab.Group as="div" className="mt-2">
                  <div className="border-b border-gray-200">
                    <Tab.List className="-mb-px flex space-x-8 px-4">
                      {navigation.categories.map((category) => (
                        <Tab
                          key={category.name}
                          className={({ selected }) =>
                            classNames(
                              selected ? 'border-indigo-600 text-indigo-600' : 'border-transparent text-gray-900',
                              'flex-1 whitespace-nowrap border-b-2 px-1 py-4 text-base font-medium border-none'
                            )
                          }
                        >
                          {category.name}
                        </Tab>
                      ))}
                    </Tab.List>
                  </div>
                  <Tab.Panels as={Fragment}>
                    {navigation.categories.map((category) => (
                      <Tab.Panel key={category.name} className="space-y-10 px-4 pb-8 pt-10">
                        <div className="grid grid-cols-2 gap-x-4">
                          {category.featured.map((item) => (
                            <div key={item.name} className="group relative text-sm">
                              <div className="aspect-h-1 aspect-w-1 overflow-hidden rounded-lg bg-gray-100 group-hover:opacity-75">
                                <img
                                  src={item.imageSrc}
                                  alt={item.imageAlt}
                                  className="object-cover object-center"
                                />
                              </div>
                              <a href={item.href} className="mt-6 block font-medium text-gray-900">
                                <span className="absolute inset-0 z-10" aria-hidden="true" />
                                {item.name}
                              </a>
                              <p aria-hidden="true" className="mt-1">
                                Shop now
                              </p>
                            </div>
                          ))}
                        </div>
                        {category.sections.map((section) => (
                          <div key={section.name}>
                            <p id={`${category.id}-${section.id}-heading-mobile`} className="font-medium text-gray-900">
                              {section.name}
                            </p>
                            <ul
                              role="list"
                              aria-labelledby={`${category.id}-${section.id}-heading-mobile`}
                              className="mt-6 flex flex-col space-y-6"
                            >
                              {section.items.map((item) => (
                                <li key={item.name} className="flow-root">
                                  <p className="-m-2 block p-2 text-gray-500">{'item.name'}</p>
                                </li>
                              ))}
                            </ul>
                          </div>
                        ))}
                      </Tab.Panel>
                    ))}
                  </Tab.Panels>
                </Tab.Group>

                <div className="space-y-6 border-t border-gray-200 px-4 py-6">
                  {navigation.pages.map((page) => (
                    <div key={page.name} className="flow-root">
                      <a href={page.href} className="-m-2 block p-2 font-medium text-gray-900">
                        {page.name}
                      </a>
                    </div>
                  ))}
                </div>

                <div className="space-y-6 border-t border-gray-200 px-4 py-6">
                  <div className="flow-root">
                    <a href="/" className="-m-2 block p-2 font-medium text-gray-900">
                      Sign in
                    </a>
                  </div>
                </div>

                <div className="border-t border-gray-200 px-4 py-6">
                  <a href="/" className="-m-2 flex items-center p-2">
                    <img src="" alt="" className="block h-auto w-5 flex-shrink-0" />
                    <span className="ml-3 block text-base font-medium text-gray-900">CAD</span>
                    <span className="sr-only">, change currency</span>
                  </a>
                </div>
              </Dialog.Panel>
            </Transition.Child>
          </div>
        </Dialog>
      </Transition.Root>

      <header className="relative bg-white">
        <nav aria-label="Top" className="mx-auto">
          <div className="border-b border-gray-200">
            <div className="flex h-16 items-center px-11">
            <button
              type="button"
              aria-label="Close menu"
              className="-m-2 inline-flex items-center justify-center rounded-md p-2 text-gray-400"
              onClick={() => setOpen(false)}
            >
            </button>

              <div className="ml-4 flex lg:ml-0">
                <Link to="/">
                  <span className="sr-only">Your Company</span>
                  <img
                    src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSjZFm8tnDeWKuk5G9ryzqBk2oxZpLbqOqis6oWBx5WfF6OxIPXMeq3QFKAL2NBvxhNSvY&usqp=CAU"
                    alt="Logo"
                    className="h-12 w-16 mr-2"
                  />
                </Link>
              </div>
              <div className="ml-auto flex items-center">
                <div className="hidden lg:flex lg:flex-1 lg:items-center lg:justify-end lg:space-x-6">
                  {auth.user ? (
                    <div>
                    <Avatar
                    className="text-white"
                    onClick={handleUserClick}
                    aria-controls={open ? 'basic-menu' : undefined}
                    aria-haspopup="true"
                    aria-expanded={open ? 'true' : undefined}
                    sx={{
                      bgcolor: '#000',
                      color: 'white',
                      cursor: 'pointer',
                    }}
                  >
                    {auth.user?.firstName[0].toUpperCase()}
                  </Avatar>

                      <Menu
                        id="basic-menu"
                        anchorEl={anchorEl}
                        open={openUserMenu}
                        onClose={handleCloseUserMenu}
                        MenuListProps={{
                          'aria-labelledby': 'basic-button',
                        }}
                      >
                        <MenuItem onClick={handleMyOrderClick}>
                          {auth.user?.role === 'ROLE_ADMIN' ? 'Admin Dashboard' : 'My Orders'}
                        </MenuItem>
                        <MenuItem onClick={handleMyOrderProfileClick}>
                          {auth.user?.role === 'ROLE_CUSTOMER' ? 'User Profile' : ''}
                        </MenuItem>
                        <MenuItem onClick={handleLogout}>Logout</MenuItem>
                      </Menu>
                    </div>
                  ) : (
                    <Button onClick={handleOpen} className="text-xs font-medium text-gray-700 hover:text-gray-800 py-4 px-4">
                    Login/Signup</Button>
                  )}
                </div>

                {/* Search Box */}
                <form onSubmit={handleSearch} className="flex items-center w-full">
                  <label htmlFor="search-input" className="sr-only">Search products</label>
                  <input
                    type="text"
                    id="search-input"
                    className="border p-2 rounded-l w-full"
                    placeholder="Search products..."
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                  />
                  <button type="submit" aria-label="search" className="bg-green-900 text-white p-2 rounded-r">
                    <MagnifyingGlassIcon className="h-5 w-5" />
                  </button>
                </form>
            
                {/* Cart */}
                <div className="ml-4 flow-root lg:ml-6">
                  <Button onClick={() => navigate('/cart')} className="group -m-2 flex items-center p-2">
                    <div className="relative">
                    <Badge badgeContent={totalItems} color="secondary">
                        <ShoppingCartIcon fontSize="large" className="h-6 w-6 flex-shrink-0 text-gray-400 group-hover:text-gray-500"
                      aria-hidden="true"/>
                    </Badge>
                </div>

                  </Button>
                </div>
              </div>
            </div>
          </div>
        </nav>
      </header>
      <AuthModal handleClose={handleClose} open={openAuthModal} />
    </div>
  );
}
