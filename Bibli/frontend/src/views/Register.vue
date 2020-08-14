<template>
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <img id="createacctimg" src="@/assets/img/createaccount.png" alt="bilbi text logo">
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <label for="familyName">Choose a Family Name</label>
      <input
        type="text"
        id="familyName"
        class="form-control"
        placeholder="The Sparkly Unicorns"
        v-model="user.familyName"
        required
        autofocus
      />
      <label for="firstName">First Name</label>
      <input
        type="text"
        id="firstName"
        class="form-control"
        placeholder="Amy"
        v-model="user.firstName"
        required
        autofocus
      />
      <label for="lastName">Last Name</label>
      <input
        type="text"
        id="lastName"
        class="form-control"
        placeholder="Pond"
        v-model="user.lastName"
        required
        autofocus
      />
      <label for="age">Age</label>
      <input
        type="text"
        id="age"
        class="form-control"
        placeholder="31"
        v-model="user.age"
        required
        autofocus
      />
      <label for="username">Choose a Username</label>
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
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <router-link :to="{ name: 'login' }">Have an account?</router-link>
      <button class="hvr-bounce-in" type="submit">
        Create Account
      </button>
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      addFamilyName: 'false',
      user: {
        familyName: '',
        firstName: '',
        lastName:'',
        age: '',
        username: '',
        password: '',
        confirmPassword: '',
        role: 'admin'
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201 || response.status == 204) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'It is possible this username already exists.Please pick a different username';
            }
            else if (response.status === 418) {
              this.registrationErrorMsg = 'It is possible this family name already exists.Please pick a different username';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>

a {
  text-decoration: none;
}

#register {
  display: flex;
  flex-direction: column;
  align-items: center;
}

#createacctimg {
  width: 100vh;
  align-items: center;
}

.form-register {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  width: 50vw;
}

button {
  background-color: #ffbd59;
  color: white;
  width: 200px;
  padding: 10px;
  border: none;
  border-radius: 5px;
  margin: 10px;
  font-size: 18px;
}

input {
  padding: 10px;
  border-radius: 5px;
  border: none;
  width: 30vw;
}

a {
  text-decoration: none;
  color: #545454;
}

label {
  text-decoration: none;
  color: #545454;
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
