<template>
    <div class="activity-container">
        <img id="addreadingactivity" src="@/assets/img/logreading.png" alt="Add to Reading Log">
        <form class="form-logactivity" @submit.prevent="updateLog">
            <label v-if="$store.state.userDetails.role === 'ROLE_ADMIN'">Reader</label>
            <select v-if="$store.state.userDetails.role === 'ROLE_ADMIN'" v-model="readingLog.username" v-on:change="getBookList(readingLog.username)">
                <option v-for="familyMember in $store.state.familyMembers" v-bind:key="familyMember.username">{{familyMember.username}}</option>
                <!--------------------------------^ changed from first name to username ^--------------------------------------->
            </select>
            <label>Book</label>
            <select v-model="bookTitle" v-on:mouseover="getBookList(readingLog.username)">
                <option v-for="book in bookList" v-bind:key="book.bookId">{{book.title}}</option>
            </select>
            
            <label>Book Format</label>
            <select v-model="readingLog.bookFormat">
                <option>Paper</option>
                <option>e-Book</option>
                <option>Audiobook</option>
                <option>I read this book aloud to others</option>
                <option>This book was read aloud to me</option>
                <option>Other</option>
            </select>
            <label>How many minutes did you read?</label>
            <input 
                type="number"
                id="timeread"
                class="form-control"
                v-model.number="readingLog.timeRead"
                required
                autofocus
            />
            <label>Notes about what you read:</label>
            <textarea name="notes" form="activity-form" cols="25" rows="5" v-model="readingLog.notes"></textarea>
            <div class="bookcomplete">
                <label for="checkbox">Did you complete this book?</label>
                <input id="checkbox" type="checkbox" v-model="readingLog.completed"/>
            </div>
            <router-link class="addbooknav" v-bind:to="{ name: 'Add Book' }">Don't see the book you want to log? Add it here.</router-link>
            <div class="add-book-buttons">
            <button class="hvr-bounce-in" type="submit"><b-icon icon="plus-circle"></b-icon> Add to Log</button>
            <router-link v-bind:to="{ name: 'home' }">
                <button id="sidebar-nav" class="hvr-bounce-in"><b-icon icon="house-door"></b-icon> Return Home</button>
            </router-link>
            </div>
        </form>
    </div>
</template>

<script>

import userService from '@/services/UserService'

export default {
    name: 'log-reading-activity',
    data () {
        return {
            bookTitle: '',
            readingLog: {
                username: '',
                bookId: '',
                format: '',
                timeRead: 0,
                notes: 'none',
                completed: false
            },
            bookList: ''
        }
    },
    created() {
        this.$store.state.userDetails.role === 'ROLE_USER' ? this.readingLog.username = this.$store.state.userDetails.username : '';
    },
    methods: {
        getBookList(username) {
            
            let filteredBookList= [];
            let anotherArray= [];
            this.$store.state.familyMembers.forEach( familyMember => {
                if(familyMember.username==username) {
                    let bookList = familyMember.books;

                    for (let book of bookList) {
                      
                      if (!filteredBookList.includes(book.title)){
                        anotherArray.push(book)
                        filteredBookList.push(book.title)
                        
                      }
                    }
                } 
            })
            this.bookList = anotherArray;
        },
        getBookId(username, bookTitle) {
            this.$store.state.familyMembers.forEach( familyMember => {

                if(familyMember.username==username) {

                    familyMember.books.forEach( book => {
                    if(bookTitle == book.title) {
                    this.readingLog.bookId = book.bookId;
                    }
                    })
                } 
            })
        },
       updateLog() {
                this.getBookId(this.readingLog.username, this.bookTitle);
                userService
                .updateLog(this.readingLog)
                .then((response) => {
                    if (response.status == 204) {
                       // this.$store.commit('UPDATE_READING_LOG', response.data.title, response.data.username, response.data.timeRead);
                       this.$store.commit('UPDATE_READING_LOG', this.readingLog);
                       console.log(this.readingLog);
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
                        this.registrationErrorMsg = 'There was a problem updating this log.';
                    }
                })
        },
        clearErrors() {
            this.registrationErrors = false;
            this.registrationErrorMsg = 'There was a problem updating this log.';
        }
    }  
}
</script>

<style>

.activity-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
}

.form-logactivity {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
}

#addreadingactivity {
    width: 70%;
}

.addbooknav {
    color: #545454;
}

textarea {
    border: none;
    border-radius: 10px;
    padding: 10px;
    margin-top: 10px;
}

</style>