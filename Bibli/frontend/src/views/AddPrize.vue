<template>
    <div id="addPrize">
        <img src="@/assets/img/addaprize.png" class="add-prize-img" alt="Active Prizes"/>
        <form class="form-addbook" @submit.prevent="addPrize">

            <input 
                type="text"
                id="name"
                class="form-control"
                placeholder="Name of Prize"
                v-model="prize.name"
                required
                autofocus
            />
            <label for="description">Prize Description</label>
            <textarea name="desctiption" form="activity-form" cols="25" rows="5" v-model="prize.description"></textarea>
            <input 
                type="text"
                id="milestone"
                class="form-control"
                placeholder="Minutes of Reading Required to Earn Prize"
                v-model="prize.milestone"
                required
                autofocus
            />
            <label>Earning Period Start Date</label>
             <input 
                type="date"
                id="startDate"
                class="form-control"
                placeholder="Start Date"
                v-model="prize.startDate"
                required
                autofocus
            />
            <label>Earning Period End Date</label>
            <input 
                type="date"
                id="endDate"
                class="form-control"
                placeholder="End Date"
                v-model="prize.endDate"
                required
                autofocus
            />

            <input 
                type="text"
                id="maxPrizes"
                class="form-control"
                placeholder="Number of Prizes Available"
                v-model="prize.maxPrizes"
                required
                autofocus
            />

            <label>Is this prize for children, adults, or both?</label>
                 <select v-model="adultOrChild" v-on:change="selectUserRole(adultOrChild)">
                     <option>Adult</option>
                     <option>Child</option>
                      <option>Both</option>
                </select>              

            <div class="add-book-buttons">
            <button class="hvr-bounce-in" type="submit"><b-icon icon="plus-circle"></b-icon> Add Prize</button>
            <router-link :to="{ name: 'home' }">
                <button class="hvr-bounce-in"><b-icon icon="x-circle"></b-icon> Cancel</button>
            </router-link>
            </div>
        </form>
    </div>
</template>

<script>

import userService from '@/services/UserService'

export default {
    name: 'add-prize',
    data() {
        return {
            adultOrChild: '',
            prize: {
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
        }
    },
    
    registrationErrors: false,
    registrationErrorMsg: 'There was a problem adding this prize.',

    methods: {
        testMethod() {
            console.log(this.prize)
        },
        addPrize() {
            userService
                .addPrize(this.prize)
                .then((response) => {
                    if (response.status == 201 || response.status == 200) {
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
                        this.registrationErrorMsg = 'It looks like you already added this prize. Please try again.';
                    }
                })
        },
        clearErrors() {
            this.registrationErrors = false;
            this.registrationErrorMsg = 'There was a problem adding this prize.';
        },

        selectUserRole() {
      if (this.adultOrChild == 'Adult') {
                this.prize.userRole = 'ROLE_ADMIN';
            }
            else if (this.adultOrChild == 'Child') {
                this.prize.userRole = 'ROLE_USER';
            }
            else {
                this.prize.userRole = 'BOTH'
            }
    }
    }
}
</script>

<style scoped>

#addPrize {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
}

.add-prize-img {
    width: 70%;
}

</style>