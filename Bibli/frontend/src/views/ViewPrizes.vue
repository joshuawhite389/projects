<template>
  <div class="prize-container">

    <img src="@/assets/img/prizesearned.png" class="label-img" alt="Prizes Earned"/>
    <div class="prizes-awarded">
      <b-card v-for="prize in $store.state.familyPrizes" v-bind:key="prize.id"
        tag="article"
        style="max-width: 20rem;"
        class="mb-2"
        id="award-card"
      >
        <b-card-title><b-icon icon="award"></b-icon> {{prize.prizeName}}</b-card-title>
        <b-card-text>
          {{prize.prizeDescription}}
        </b-card-text>
        <b-card-text>
          Recipient: {{prize.username}}
        </b-card-text>
      </b-card>
    </div>

    <img src="@/assets/img/activeprizes.png" class="label-img" alt="Active Prizes"/>
    <div class="active-prizes"> 
      <b-card id="active-prize-card" v-for="prize in $store.state.prizes" v-bind:key="prize.prizeId"
        tag="article"
        style="max-width: 20rem;"
        class="mb-2"
        >
          <b-card-title>{{prize.name}}</b-card-title>
          <b-card-text>{{prize.description}}</b-card-text>
          <b-card-text>Read {{prize.milestone}} minutes</b-card-text>
          <b-card-text>From: {{prize.startDate}} To: {{prize.endDate}}</b-card-text>
          <div v-if="$store.state.userDetails.role === 'ROLE_ADMIN'">
            <button @click="getPrizeToEditId(prize.prizeId)" id="prize-button" class="hvr-bounce-in" v-b-toggle.edit-prize><b-icon icon="pencil-square"></b-icon></button>
            <button v-on:click="deletePrize(prize.prizeId)"  id="prize-button" class="hvr-bounce-in" v-b-toggle.delete-prize><b-icon icon="trash"></b-icon></button>
          </div>
      </b-card>
    </div>
  


    <b-collapse id="edit-prize" class="edit-prize">
      <h2>Edit Prize</h2>
      <form class="form-addbook" @submit.prevent="updatePrize">
        <select v-model="prizeToEdit.name" v-on:change="getPrizeToEditId">
          <option v-for="prize in $store.state.prizes" v-bind:key="prize.prizeName">
            {{prize.name}}
          </option>
        </select>
        <label for="description">Prize Description</label>
        <textarea name="description" form="activity-form" cols="25" rows="5" v-model="prizeToEdit.description"></textarea>  
        <input 
          type="text"
          id="milestone"
          class="form-control"
          placeholder="Minutes Required for Prize"
          v-model="prizeToEdit.milestone"
          required
          autofocus
        />
        <input 
          type="date"
          id="startDate"
          class="form-control"
          placeholder="Start Date"
          v-model="prizeToEdit.startDate"
          required
          autofocus
        />
        <input 
          type="date"
          id="endDate"
          class="form-control"
          placeholder="End Date"
          v-model="prizeToEdit.endDate"
          required
          autofocus
        />
        <input 
          type="text"
          id="maxPrizes"
          class="form-control"
          placeholder="Number of Prizes Available"
          v-model="prizeToEdit.maxPrizes"
          required
          autofocus
        />
        <label>Is this prize for children, adults, or both?</label>
        <select v-model="adultOrChild" v-on:change="selectUserRole(adultOrChild)">
          <option>Adult</option>
          <option>Child</option>
          <option>Both</option>
        </select>
        <div class="edit-buttons">     
          <button class="hvr-bounce-in" type="submit"><b-icon icon="check-circle"></b-icon> Update</button>
          <button class="hvr-bounce-in" v-b-toggle.edit-prize><b-icon icon="x-circle"></b-icon> Cancel</button>
        </div>
      </form>
    </b-collapse>








<!-- assign prizes to users -->
  
    <div class="award-prize" v-if="$store.state.userDetails.role === 'ROLE_ADMIN'">
    <img src="@/assets/img/awardaprize.png" class="label-img" alt="Active Prizes"/>
      <form class="form-awardPrize" @submit.prevent="awardPrize">
        <label>Family Member</label>
        <select v-model="prizeDetails.username">
          <option v-for="user in $store.state.familyMembers" v-bind:key="user.username">{{user.username}}</option>
        </select>
        <label>Prize Earned</label>
        <select v-model="prizeName" v-on:change="getPrizeId">
          <option v-for="prize in $store.state.prizes" v-bind:key="prize.prizeName">{{prize.name}}</option>
        </select>

        <button class="hvr-bounce-in" type="submit">Award Prize</button>
      </form>
    
    </div>

    <hr>
    

  </div>
</template>

<script>
import userService from "@/services/UserService";

export default {
  data() {
    return {

      prizeIdToDelete: '',
      prizeNameToDelete: '',

      adultOrChild: 'Both',
      prizeName: '',
      prizeDetails: {
        username: "",
        prizeId: "",
        
      },
      prizeToEdit: {
                prizeId: '',
                name: '',
                description: '',
                milestone: '',
                userRole: '',
                maxPrizes: '',
                startDate: '',
                endDate: '',
                familyName: this.$store.state.userDetails.familyName,
                isActive: true
            }
    };
  },
  registrationErrors: false,
  registrationErrorMsg: 'There was a problem adding this book.',

  created() {
    userService
      .getPrizeInfo(
        this.$store.state.userDetails.familyName.replace(/ /g, "%20")
      )
      .then((response) => {
        if (response.status == 200 || response.status == 204) {
          this.$store.commit("SET_FAMILY_PRIZES", response.data);
        }
      });

      
  },

  methods: {
    getPrizeId() {
      this.$store.state.prizes.forEach(prize => {
        if (this.prizeName == prize.name) {
          this.prizeDetails.prizeId = prize.prizeId
          
          
        }
      })
console.log(this.prizeDetails)
    },

    getPrizeToEditId(id) {
      this.prizeToEdit.prizeId = id

      this.$store.state.prizes.forEach(prize => {
        if (this.prizeToEdit.prizeId == prize.prizeId) {
          this.prizeToEdit.name = prize.name
          this.prizeToEdit.description = prize.description
          this.prizeToEdit.milestone = prize.milestone
          this.prizeToEdit.maxPrizes = prize.maxPrizes
          this.prizeToEdit.startDate = prize.startDate
          this.prizeToEdit.endDate = prize.endDate
          this.prizeToEdit.userRole = prize.userRole
          
        }
        
      })
      
    },

    getPrizeToDelteId() {
      this.$store.state.prizes.forEach(prize => {
        if (this.prizeNameToDelete == prize.name) {
          this.prizeIdToDelete = prize.prizeId
          console.log(this.prizeIdToDelete)
        }
      })

    },

    awardPrize() {
            userService
                .awardPrize(this.$store.state.userDetails.familyName.replace(/ /g, "%20"), this.prizeDetails)
                .then((response) => {
                    if (response.status == 200) {
                      userService
                      .getPrizeInfo(
                        this.$store.state.userDetails.familyName.replace(/ /g, "%20")
                      )
                      .then((response) => {
                        if (response.status == 200 || response.status == 204) {
                          this.$store.commit("SET_FAMILY_PRIZES", response.data);
                        }
                      })
                      .catch((error) => {
                        const response = error.response;
                        this.registrationErrors = true;
                        if (response.status === 400) {
                          this.registrationErrorMsg = 'It looks like you already added this title. Please try again.';
                        }
                      });
                        this.$router.push({
                            path: '/viewPrizes',
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
        },

        updatePrize() {
          console.log(this.prizeToEdit)
          userService
                .updatePrize(this.prizeToEdit)
                .then((response) => {
                    if (response.status == 200) {
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


        

        selectUserRole() {
      if (this.adultOrChild == 'Adult') {
                this.prizeToEdit.userRole = 'ROLE_ADMIN';
            }
            else if (this.adultOrChild == 'Child') {
                this.prizeToEdit.userRole = 'ROLE_USER';
            }
            else {
                this.prizeToEdit.userRole = 'BOTH'
            }
    },

    deletePrize(id) {
      userService
                .deletePrize(id)
                .then((response) => {
                    if (response.status == 200) {
                      
                      let filteredPrizes = 
                      this.$store.state.prizes.filter( prize => 
                        
                        prize.prizeId !== id

                      );
        
                      this.$store.state.prizes = filteredPrizes;
                      this.$router.push({
                            path: '/viewPrizes',
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

    }



  },
};
</script>

<style>

.prize-container {
  display: flex;
  flex-direction: column;
  align-content: center;
  align-items: center;
  text-align: center;
  color: #545454;
}

#prize-button {
  width: 50px;
}

#award-card {
  box-shadow: 0px 0px 15px 0px gold;
}

#active-prize-card {
  box-shadow: 0px 0px 15px 0px #7ed957;
}

.label-img {
  width: 30%;
}

.edit-prize {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.edit-buttons {
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-bottom: 20px;
}

.prizes-awarded {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  width: 70vw;
}

.active-prizes {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  width: 70vw;
}

.assign-prize {
  display: flex;
  flex-direction: column;
  background: white;
  border-radius: 5px;
}

.award-prize {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  border-radius: 5px;
  background: white;
  border-radius: 5px;
  margin-top: 25px;
  padding: 10px;
}

</style>