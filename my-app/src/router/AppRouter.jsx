import { useRoutes, Navigate  } from 'react-router-dom';
import Home from '../pages/Home';
import UserInfo from '../pages/UserInfo';
import About from '../pages/About';
import Login from '../pages/Login';
import MainLayout from '../layout/MainLayout';

export default function AppRouter() {
  const isLoggedIn = !!localStorage.getItem("accessToken");
  const routes = useRoutes([
    {
      path: '/login',
      element: isLoggedIn ? <Navigate to="/" /> : <Login />
    },
    {
      path: '/',
      element: isLoggedIn ? <MainLayout /> : <Navigate to="/login" />,
      children: [
        { path: 'home', element: <Home /> },
        { path: 'user', element: <UserInfo /> },
        { path: 'about', element: <About /> }
      ]
    }
  ]);

  return routes;
}