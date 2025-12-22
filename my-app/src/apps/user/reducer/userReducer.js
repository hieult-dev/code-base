export const initialUser = {
    name: 'Tu',
    age: 21,
    city: 'Hà Nội'
}

export function userReducer(state, action) {
    switch (action.type) {
        case 'INCREASE_AGE':
            return { ...state, age: state.age + 1 }

        case 'CHANGE_CITY':
            return { ...state, city: action.payload }

        default:
            return state
    }
}
