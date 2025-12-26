import { useRoutes, Navigate } from 'react-router-dom';
import Home from '../pages/Home';
import UserInfo from '../../apps/user/UserInfo';
import About from '../pages/About';
import Login from '../pages/Login';
import MainLayout from '../layout/MainLayout';
import { useUserStore } from '../store/user';
import { UserRole } from '@/apps/user/model/User';
import CourseManage from '@/apps/course/view/CourseManage';

export default function AppRouter() {
  const userRole = useUserStore((state) => state.userRole)
  const isLoggedIn = !!useUserStore((state) => state.authentication);
  const isAdmin = userRole === UserRole.ADMIN

  const routes = useRoutes([
    {
      path: '/login',
      element: <Login />
    },
    {
      path: '/',
      element: isLoggedIn ? <MainLayout /> : <Navigate to="/login" />,
      children: [
        { index: true, element: <Home /> },
        { path: 'home', element: <Home /> },
        { path: 'user', element: <UserInfo /> },
        { path: 'about', element: <About /> },

        {
          path: 'admin/course',
          element: isAdmin
            ? <CourseManage />
            : <Navigate to="/home" />
        }
      ]
    }
  ]);

  return routes;
}