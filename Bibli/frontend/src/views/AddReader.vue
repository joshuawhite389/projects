<template>
  <div id="add-reader">
    <img id="newreader" src="@/assets/img/NewReader.png" alt="Add a New Reader">
    <form class="form-addreader" @submit.prevent="addUser">
      <input
        type="text"
        id="firstName"
        class="form-control"
        placeholder="Reader's First Name"
        v-model="user.firstName"
        required
        autofocus
      />
      <input
        type="text"
        id="lastName"
        class="form-control"
        placeholder="Reader's Last Name"
        v-model="user.lastName"
        required
        autofocus
      />
      <input
        type="text"
        id="age"
        class="form-control"
        placeholder="Reader's Age"
        v-model="user.age"
        required
        autofocus
      />
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Reader's Username"
        v-model="user.username"
        required
        autofocus
      />
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Reader's Password"
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
      <label>Is this reader an adult or a child?</label>
      <select v-model="adultOrChild" v-on:change="setReaderRole(adultOrChild)">
        <option>Adult</option>
        <option >Child</option>
      </select>
      <div class="add-book-buttons">
      <button class="hvr-bounce-in" type="submit"><b-icon icon="plus-circle"></b-icon> Add Reader</button>
      <router-link :to="{ name: 'home' }">
        <button class="hvr-bounce-in"><b-icon icon="x-circle"></b-icon> Cancel</button>
      </router-link>
      </div>
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'add-reader',
  data() {
    return {
      adultOrChild: '',
      user: {
        familyName: this.$store.state.userDetails.familyName,
        firstName: '',
        lastName:'',
        age: '',
        username: '',
        password: '',
        confirmPassword: '',
        role: ''
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    setReaderRole() {
      this.adultOrChild == 'Adult' ? this.user.role = 'admin' : this.user.role = 'user';
      console.log(this.user.role);
    },
    addUser() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .addUser(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/',
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

<style>

a {
  text-decoration: none;
}

#newreader {
  width: 80%;
}

#add-reader {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.form-addreader {
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
  border-radius: 10px;
  margin: 10px;
  font-size: 18px;
}

input {
  padding: 10px;
  margin: 10px;
  border-radius: 10px;
  border: none;
}

select {
  padding: 10px;
  margin: 10px;
  border-radius: 10px;
  border: none;
  width: 150px;
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

label {
  color: #545454;
}

</style>