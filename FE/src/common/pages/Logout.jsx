import { useNavigate } from "react-router-dom";
import { useUserStore } from "../store/user";
import { logout } from "../auth/api/rest/authApi";

export default function LogoutButton() {
    const navigate = useNavigate();
    const resetUserStore = useUserStore(state => state.resetUserStore);

    const handleLogout = async () => {
        try {
            await logout();
        } catch (e) {
            console.error("Logout failed:", e);
        } finally {
            resetUserStore();
            navigate("/login", { replace: true });
        }
    };

    return (
        <button
            onClick={handleLogout}
            style={{
                color: "#fff",
                background: "transparent",
                border: "none",
                padding: "6px",
                borderRadius: "4px",
                cursor: "pointer",
                textAlign: "left"
            }}
        >
            Logout
        </button>
    );
}
