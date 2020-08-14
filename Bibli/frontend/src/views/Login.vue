<template>
  <div id="login" class="text-center">
    <form class="form-signin" @submit.prevent="login">
      <img id="loginimg" src="@/assets/img/login.png" alt="Welcome, reader! Please log in.">
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <router-link :to="{ name: 'register' }">Need an account?</router-link>
      <button class="hvr-bounce-in" type="submit">Sign in</button>
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'login',
  components: {},
  data() {
    return {
      user: {
        username: '',
        password: ''
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit('SET_AUTH_TOKEN', response.data.token);
            this.$store.commit('SET_USER', response.data.user);
            this.$store.commit('SET_USER_DETAILS', response.data.userDetails);
            this.$store.commit('SET_USER_BOOKS', response.data.userBooks);
            this.$router.push('/');
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>

#login {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#loginimg {
  width: 100vh;
  text-align: center;
  align-items: center;
  justify-content: center;
}

.form-signin {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  width: 80vh;
}

button {
  background-color: #ffbd59;
  color: white;
  width: 180px;
  padding: 10px;
  border: none;
  border-radius: 5px;
  margin: 10px;
  font-size: 16px;
}

a {
  text-decoration: none;
  color: #545454;
}

input {
  padding: 10px;
  margin: 10px;
  border-radius: 5px;
  border: none;
}

.hvr-bounce-in {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  -webkit-transition-duration: 0.5s;
  transition-duration: 0.5s;
}

.hvr-bounce-in:hover, .hvr-bounce-in:focus, .hvr-bounce-in:active {
  -webkit-transform: scale(1.2);
  transform: scale(1.2);
  -webkit-transition-timing-function: cubic-bezier(0.47, 2.02, 0.31, -0.36);
  transition-timing-function: cubic-bezier(0.47, 2.02, 0.31, -0.36);
}

</style>
