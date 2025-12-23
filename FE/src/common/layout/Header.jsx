export default function Header() {
    return (
        <header
            style={{
                height: '60px',
                background: '#1f2937',
                color: '#fff',
                display: 'flex',
                alignItems: 'center',
                padding: '0 20px',
                position: 'fixed',
                top: 0,
                left: 0,
                right: 0,
                zIndex: 1000
            }}
        >
            <h3>🚀 My App</h3>
        </header>
    )
}