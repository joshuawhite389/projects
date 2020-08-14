import axios from 'axios';

export default {

  login(user) {
    return axios.post('/login', user)
  },

  register(user) {
    return axios.post('/register', user)
  },

  addUser(user) {
    return axios.post('/add-user', user)
  },

  addPrize(prize) {
    return axios.post('/add-prize', prize)
  }

}
