import { useRoutes, Navigate } from 'react-router-dom';
import Home from '../pages/Home';
import UserInfo from '../../apps/user/UserInfo';
import About from '../pages/About';
import Login from '../pages/Login';
import MainLayout from '../layout/MainLayout';
import { useUserStore } from '../store/user';

export default function AppRouter() {
  const isLoggedIn = !!useUserStore(state => state.authentication);
  const routes = useRoutes([
    {
      path: '/login',
      element: <Login />
    },
    {
      path: '/',
      element: <MainLayout />,
      children: [
        {
          index: true,
          element: <Home />
        },
        { path: 'home', element: <Home /> },
        { path: 'user', element: <UserInfo /> },
        { path: 'about', element: <About /> }
      ]
    }
  ]);

  return routes;
}