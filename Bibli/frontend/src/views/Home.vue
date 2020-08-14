<template>

<div class="home-container">

  <div class="recent-activity">
    
    <h3>{{userName}}'s  Recent  Activity</h3>
    <label v-if="$store.state.userDetails.role === 'ROLE_ADMIN'">Whose dashboard would you like to see?</label>
    <select v-if="$store.state.userDetails.role === 'ROLE_ADMIN'" v-model="userName" v-on:change="filterLogs">
      <option v-for="familyMember in $store.state.familyMembers" v-bind:key="familyMember.username">{{familyMember.username}}</option>
                <!--------------------------------^ changed from first name to username ^--------------------------------------->
    </select>
    <table class="log-table">
      <tr>
        <th>Date</th>
        <th>Title</th>
        <th>Author</th>
        <th>Minutes Read</th>
        <th>Format</th>
        <th>Notes</th>
      </tr>
      <tr v-for="log in mostRecentLogsOfChosenUser" v-bind:key="log.log">
        <td>{{log.logDate}}</td>
        <td>{{log.bookTitle}}</td>
        <td>{{log.author}}</td>
        <td>{{log.timeRead}}</td>
        <td>{{log.bookFormat}}</td>
        <td>{{log.notes}}</td>
      </tr>
    </table>
    <div class="activity-options">
      <router-link v-bind:to="{ name: 'Log Reading Activity' }">
        <button id="log-button" class="hvr-bounce-in"><b-icon icon="pencil-square"></b-icon> Log Reading Activity</button>
      </router-link>
      <router-link v-bind:to="{ name: 'Add Book' }">
        <button id="log-button" class="hvr-bounce-in"><b-icon icon="book"></b-icon> Add Book to Library</button>
      </router-link>
    </div>
  </div>

  <div class="family">
    <div class="family-info">
      <h2>{{this.$store.state.userDetails.familyName}} Stats <b-icon icon="clipboard-data"></b-icon></h2>
      <br>
      <table class="stat-table">
        <tr>
          <th>Reader</th>
          <th>Total Minutes Read</th>
          <th>Books Read</th>
        </tr>
        <tr v-for="familyMember in familyMemberReadingStatsTotal" v-bind:key="familyMember.username">
          <td>{{familyMember.username}}</td>
          <td>{{familyMember.minutesRead}}</td>
          <td>{{familyMember.booksRead}}</td>
        </tr>
      </table>
      <br>
      <router-link v-bind:to="{ name: 'Add Reader' }" v-if="$store.state.userDetails.role=='ROLE_ADMIN'">
        <button id="sidebar-nav" class="hvr-bounce-in"><b-icon icon="person-plus"></b-icon> Add a Reader</button>
      </router-link>
    </div>

    <div class="goals">
      <h2>Goals & Prizes</h2>
      <div class="filter-goals">

        <div v-for="prize in filteredPrizes" v-bind:key="prize.id">
          <p><b-icon icon="award"></b-icon>Read {{prize.milestone}} minutes by {{prize.endDate}} to get {{prize.name}}</p>
          <b-progress :value="findCurrentUserReadingMinutes(prize.startDate,prize.endDate)" :max="prize.milestone" show-progress class="mb-3"></b-progress>
        </div>

        <router-link v-bind:to="{ name: 'Add Prize' }" v-if="$store.state.userDetails.role=='ROLE_ADMIN'">
          <button id="log-button" class="hvr-bounce-in"><b-icon icon="star"></b-icon> Add New Prize</button>
        </router-link>
      </div>
    </div>

  </div>

</div>

</template>

<script>

import userService from '@/services/UserService';

export default {
  name: 'home',
  data() {
    return {
      mostRecentLogsOfChosenUser: '',
      filteredPrizes: '',
      userName: this.$store.state.userDetails.username,
      // v model drop down to this variable ^^
      familyMemberReadingStatsTotal: '',
      currentUserTotalMinutesRead: '',
      dropDownPrizeRole: 'BOTH',
      adultOrChild: 'Both',
    }
  },
  
  created() {
        userService.getFamilyMembers(this.$store.state.userDetails.familyName.replace(/ /g, "%20"))
        .then( response => {
             if (response.status == 200 || response.status == 204) {
                this.$store.commit('SET_FAMILY_MEMBERS', response.data);
                this.setMostRecentUserBooks();
                this.totalReadingStats();
      }
    })
    .catch(error => {
          const response = error.response;

          if (response.status === 400) {
            console.log('there was a problem')
          }
        });

        userService.getPrizes(this.$store.state.userDetails.familyName.replace(/ /g, "%20"))
        .then(response => {
          if (response.status == 200 || response.status == 204) {
            this.$store.commit('ADD_TO_PRIZES', response.data);
            this.filterPrizes();
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 400) {
            console.log('there was a problem')
          }
        });

        userService.getMostRecentLogs(this.$store.state.userDetails.familyName)
                .then(response => {
          if (response.status == 200 || response.status == 204) {
            this.$store.commit('ADD_MOST_RECENT_LOGS', response.data);
            this.filterLogs();
          }
        });

  
    
  },
    computed: {
    currentlyReading() {
    return this.$store.state.userBooks.filter((books) => {
        if(!books.completed) return books; 
    });  
    },
    finishedReading() {
    return this.$store.state.userBooks.filter((books) => {
        if(books.completed) return books; 
    });  
    },
    },
    methods: {

    filterPrizes(){
    let filteredPrizes= '';
    this.$store.state.familyMembers.forEach( member=> {
    if(this.userName==member.username) {
    if(member.role=='ROLE_ADMIN') filteredPrizes = this.$store.state.prizes.filter( prize => prize.userRole=='ROLE_ADMIN' || prize.userRole=='BOTH' );
    if(member.role=='ROLE_USER') filteredPrizes = this.$store.state.prizes.filter( prize=> prize.userRole=='ROLE_USER' || prize.userRole=='BOTH' );
    }
    })
     this.filteredPrizes = filteredPrizes;

    },

      //gets stats for family and populates local memory keeping them up to date with database

      totalReadingStats() {
      let totalReadingStats = [];

      this.$store.state.familyMembers.forEach( familyMember => {

      let familyMemberReadingStats = {
        username: '',
        minutesRead: 0,
        booksRead: 0
      }

      let booksRead = 0;
      let totalMinutes = 0;
      familyMember.books.forEach( book=> {
      
      book.completed ? booksRead += 1 : booksRead += 0;
      totalMinutes += book.timeRead;

      });

      familyMemberReadingStats.username = familyMember.username;
      familyMemberReadingStats.minutesRead = totalMinutes;
      familyMemberReadingStats.booksRead = booksRead;
      totalReadingStats.push(familyMemberReadingStats);
      })

      this.familyMemberReadingStatsTotal = totalReadingStats;

    },

    //sets recent userbooks in cache
    
    setMostRecentUserBooks() {
      this.$store.state.familyMembers.forEach( familyMember=> {
        if(familyMember.username==this.$store.state.userDetails.username) {
          this.$store.state.userBooks = familyMember.books;
        }
      })
    },

     findCurrentUserReadingMinutes(startDate, endDate) {
      let totalTimeRead = 0;
      let loggedDate = new Date();
      let theStartDate = new Date();
      let theEndDate = new Date();

      theStartDate = startDate;
      theEndDate = endDate;

      this.$store.state.familyMembers.forEach(member => {
       if (member.username == this.userName) {
          member.books.forEach(book => {

        loggedDate = book.logDate;

        if (loggedDate >= theStartDate && loggedDate <= theEndDate) {
          totalTimeRead += book.timeRead
        }
      
      })
       }
      
      })
        
      return totalTimeRead
    },

    // selectUserRole() {
    //   if (this.adultOrChild == 'Adult') {
    //             this.dropDownPrizeRole = 'ROLE_ADMIN';
    //         }
    //         else if (this.adultOrChild == 'Child') {
    //             this.dropDownPrizeRole = 'ROLE_USER';
    //         }
    //         else {
    //             this.dropDownPrizeRole = 'BOTH'
    //         }
    // },

      filterLogs() {
      this.filterPrizes();
      let logs = [];
      this.$store.state.mostRecentLogs.forEach( log => {
        if(log.username==this.userName) logs.push(log);
      })
      this.mostRecentLogsOfChosenUser = logs;
    }

    }
};
</script>


<style scoped>

.home-container {
  display: flex;
  flex-direction: column;
  width: 90vw;
  align-items: center;
  text-align: center;
  color: #545454;
}

.recent-activity {
  display: flex;
  flex-direction: column;
  background: white;
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  margin-top: 10px;
  text-align: center;
  align-items: center;
}



.log-table {
  width: 90%;
}

.stat-table {
  width: 90%;  
}

.stats-header {
  font-size: 18px;
}

.family {
  display: flex;
  flex-direction: row;
  width: 90vw;
  justify-content: space-between;
  margin-top: 20px;
  margin-bottom: 20px;
}

.family-info {
  display: flex;
  flex-direction: column;
  background: white;
  padding: 10px;
  border-radius: 5px;
  width: 60%;
}

.goals {
  display: flex;
  flex-direction: column;
  background: white;
  align-items: center;
  text-align: center;
  padding: 10px;
  border-radius: 5px;
  width: 38%;
}

#log-button {
  width: 250px;
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
