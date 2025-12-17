import { useReducer } from 'react'
import { UserContext } from '../context/UserContext'
import { userReducer, initialUser } from '../reducer/userReducer.js'

export default function AppProviders({ children }) {
    const [user, dispatch] = useReducer(userReducer, initialUser)

    const increaseCount = () => {
        dispatch({ type: 'INCREASE_AGE' })
    }

    const handleCityChange = (e) => {
        dispatch({ type: 'CHANGE_CITY', payload: e.target.value })
    }

    return (
        <UserContext.Provider value={{ user, increaseCount, handleCityChange }}>
            {children}
        </UserContext.Provider>
    )
}
