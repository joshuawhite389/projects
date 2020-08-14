import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));
const currentUserDetails = JSON.parse(localStorage.getItem('userDetails'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    userDetails: currentUserDetails || {},
    familyMembers: '',
    userBooks: '',
    prizes: {},
    familyPrizes: '',
    mostRecentLogs: ''
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    SET_USER_DETAILS(state, userDetails) {
      state.userDetails = userDetails;
      localStorage.setItem('userDetails', JSON.stringify(userDetails));
    },
    SET_FAMILY_MEMBERS(state, familyMembers) {
      state.familyMembers = familyMembers;
    },
    SET_USER_BOOKS(state, userBooks) {
      state.userBooks = userBooks;
      
    },
    ADD_TO_USER_BOOKS(state, userBooks) {
      state.userBooks.push(userBooks);
      state.familyMembers
    },

    ADD_TO_PRIZES(state, prize) {
      
      state.prizes = prize
     
    },

    SET_FAMILY_PRIZES(state, familyPrizes) {
      state.familyPrizes = familyPrizes;
    },

   UPDATE_READING_LOG(state, readingLog) {

    let bookId = readingLog.bookId;
    let username = readingLog.username;
    let timeRead= readingLog.timeRead;
    let completed = readingLog.completed;

  //first conditional checks if userBooks are being updated



  if (state.userDetails.username == username) {
   state.userBooks.forEach(book => {
    if(book.bookId===bookId) {
      book.timeRead += timeRead;
      book.completed = completed;
    }
   });
   }

   //forEach on all family members including user...data must be locally changed in both places but is redundant
    state.familyMembers.forEach( familyMember => {

        if(familyMember.username==username) {
            familyMember.books.forEach( book => {

            if(bookId === book.bookId) {
              book.timeRead += timeRead;
              book.completed = completed;
            }
            })
        } 
    })
    },

    ADD_MOST_RECENT_LOGS(state, logs) {
      state.mostRecentLogs = logs;
    },

    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      localStorage.removeItem('userDetails')
      state.token = '';
      state.user = {};
      state.userDetails = {};
      axios.defaults.headers.common = {};
    }
  }
})