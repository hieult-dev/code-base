import { useNavigate } from 'react-router-dom';
export default function Home() {
    const navigate = useNavigate();

    function goToUserPage() {
        navigate('/user', {
            state: {
                from: 'home',
                message: 'Đi từ Home sang User',
                time: Date.now()
            }
        });
    }

    return (
        <div>
            <h1>Welcome to the Home Page</h1>
            <p>This is the home component of the application.</p>
            <button onClick={goToUserPage}>Go to User Page</button>
        </div>
    )
}