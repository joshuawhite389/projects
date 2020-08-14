<template>
    <div id="addBook">
        <img id="addbookimg" src="@/assets/img/AddBook.png" alt="Add a Book">
        <div class="form-add">
        <form class="form-addbook" @submit.prevent="addBook">
            <input 
                type="text"
                id="title"
                class="form-control"
                placeholder="Book Title"
                v-model="book.title"
                required
                autofocus
            />
            <input 
                type="text"
                id="author"
                class="form-control"
                placeholder="Author"
                v-model="book.author"
                required
                autofocus
            />
            <input 
                type="text"
                id="isbn"
                class="form-control"
                placeholder="ISBN"
                v-model="book.isbn"
                required
                autofocus
            />
            <label v-if="$store.state.userDetails.role === 'ROLE_ADMIN'">Whose library would you like to add this book to?</label>
            <select v-if="$store.state.userDetails.role === 'ROLE_ADMIN'" v-model="book.username">
                <option v-for="familyMember in $store.state.familyMembers" v-bind:key="familyMember.username"> {{familyMember.username}} </option>
            </select>
            
            <div class="add-book-buttons">
            <button class="hvr-bounce-in" type="submit"><b-icon icon="plus-circle"></b-icon> Add to Library</button>
            <router-link v-bind:to="{ name: 'home' }">
                <button id="sidebar-nav" class="hvr-bounce-in"><b-icon icon="house-door"></b-icon> Return Home</button>
            </router-link>
            </div>
        </form>
        </div>
    </div>
</template>

<script>

import userService from '@/services/UserService'

export default {
    name: 'add-book',
    data() {
        return {
            book: {
                username: '',
                title: '',
                author: '',
                isbn: ''
            }
        }
    },
    created() {
        this.$store.state.userDetails.role === 'ROLE_USER' ? this.book.username = this.$store.state.userDetails.username : '';
    },
    registrationErrors: false,
    registrationErrorMsg: 'There was a problem adding this book.',
    methods: {
        addBook() {
            userService
                .addBook(this.book)
                .then((response) => {
                    if (response.status == 201) {
                        this.$store.commit('ADD_TO_USER_BOOKS', response.data);
                        this.$router.push({
                            path: '/',
                            query: { registration: 'success' }
                        });
                    }
                })
                .catch((error) => {
                    const response = error.response;
                    this.registrationErrors = true;
                    if (response.status === 400) {
                        this.registrationErrorMsg = 'It looks like you already added this title. Please try again.';
                    }
                })
        },
        clearErrors() {
            this.registrationErrors = false;
            this.registrationErrorMsg = 'There was a problem adding this book.';
        }
    }
}
</script>

<style>

#addBook {
    display: flex;
    flex-direction: column;
    align-items: center;
}

#addbookimg {
    width: 60%;
}

.form-add {
    margin-top: 20px;
}

.form-addbook {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  width: 30vw;
}

.add-book-buttons {
    display: flex;
    flex-direction: row;
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