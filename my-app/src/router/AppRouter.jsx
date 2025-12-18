import { useRoutes } from 'react-router-dom';
import Home from '../pages/Home';
import UserInfo from '../pages/UserInfo';
import About from '../pages/About';
import MainLayout from '../layout/MainLayout';

export default function AppRouter() {
  const routes = useRoutes([
    {
      path: '/',
      element: <MainLayout />,
      children: [
        { index: true, element: <Home /> },
        { path: 'user', element: <UserInfo /> },
        { path: 'about', element: <About /> }
      ]
    }
  ]);

  return routes;
}