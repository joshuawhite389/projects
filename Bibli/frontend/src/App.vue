<template>
<div class="app">

  <h1 class="toggle-button" v-b-toggle.sidebar-1 v-if="$store.state.token != ''"><b-icon icon="list"></b-icon></h1>
  <!-- <img src="@/assets/img/hamburgericon.png" class="toggle-button" v-b-toggle.sidebar-1 v-if="$store.state.token != ''" /> -->

    <div id="app" class="flex-container">
      <div class="sidebar-content">
        <b-sidebar class="sidebar" id="sidebar-1" shadow v-if="$store.state.token != ''">
          <div >
            <b-img id="bookimg" src="@/assets/img/bookimg.png" fluid thumbnail></b-img>
            <h1 class="greeting">Hello, {{$store.state.userDetails.firstName}}!</h1>
            <div>
              <router-link v-bind:to="{ name: 'home' }">
                <button id="sidebar-nav" class="hvr-bounce-in"><b-icon icon="house-door"></b-icon> Home</button>
              </router-link>
              <router-link v-bind:to="{ name: 'Add Reader' }" v-if="$store.state.userDetails.role=='ROLE_ADMIN'">
                <button id="sidebar-nav" class="hvr-bounce-in"><b-icon icon="person-plus"></b-icon> Add Reader</button>
              </router-link>
              <router-link v-bind:to="{ name: 'Library' }">
                <button id="sidebar-nav" class="hvr-bounce-in"><b-icon icon="book"></b-icon> View Library</button>
              </router-link>
              <router-link v-bind:to="{ name: 'View Prizes' }">
                <button id="sidebar-nav" class="hvr-bounce-in"><b-icon icon="star"></b-icon> Manage Prizes</button>
              </router-link>
              <router-link v-bind:to="{ name: 'logout' }">
                <button id="sidebar-nav" class="hvr-bounce-in"><b-icon icon="box-arrow-left"></b-icon> Logout</button>
              </router-link>  
            </div>
          </div>
        </b-sidebar>
      </div>

      <div class="content" v-on:click="sidebarShown = false">
        <header>
          <router-link v-bind:to="{ name: 'home' }">
            <img id="mainlogo" src="@/assets/img/logocrop.png" alt="bilbi text logo">
          </router-link>
        </header> 
        
      <router-view />

    </div>

    </div>
  </div>
</template>

<script>

export default {
  
};
</script>

<style >
html {
  background-color: #c7e5e3;
}

.toggle-button{
  width: 50px;
  position: fixed;
  margin: 10px;
  color: #545454;
}

.sidebar {
  display: flex;
  flex-direction: column;
  text-align: center;
  align-items: center;
}

#bookimg {
  border: none;
  width: 40%;
}

.greeting {
  color: tomato;
}

.app {
  font-family: 'Quicksand', sans-serif;
  font-size: 14px;
  height: 100vh;
  background-color: #c7e5e3;
}

.flex-container {
  display: flex;
  flex-direction: row;
  z-index: 1;
  background-color: #c7e5e3;
}

.flex-container .content {
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
  height: 100vh;
}

header {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

a {
  text-decoration: none;
  color: white
}

button {
  color: white;
  font-family: 'Quicksand', sans-serif;
  font-size: 12px;

}

input {
  font-family: 'Quicksand', sans-serif;
}

option {
  color: #545454;
  font-family: 'Quicksand', sans-serif;
}

@import url('https://fonts.googleapis.com/css2?family=Quicksand&display=swap');

</style>
