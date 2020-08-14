<template>
    <div class="library-container">
        
        <img id="libraryheader" src="@/assets/img/libraryheader.png" alt="Library">

        <div v-if="$store.state.userDetails.role=='ROLE_ADMIN'">
        <label>Whose library would you like to see?</label>
            <select v-model="familyMember">
                <!-- <option>View All Books</option> -->
                <option v-for="familyMember in $store.state.familyMembers" v-bind:key="familyMember.username">{{familyMember.username}}</option>
            </select>
        </div>

        <table class="library-table">
            <tr>
                <th>Title</th>
                <th>Author</th>
                <!-- <th>Minutes Read</th> -->
                <th>Completed?</th>
            </tr>
            <tr v-for="userBook in bookList" v-bind:key="userBook.bookId">
                <td>{{userBook.title}}</td>
                <td>{{userBook.author}}</td>
                <!-- <td>{{userBook.timeRead}}</td> -->
                <td v-if="userBook.completed" ><img id="star" src="@/assets/img/completedstar.png" alt="star"></td>
            </tr>
        </table>

        <div class="navigation">
            <router-link v-bind:to="{ name: 'Add Book' }">
                <button id="library-button" class="hvr-bounce-in"><b-icon icon="book"></b-icon> Add Book to Library</button>
            </router-link>
            <router-link :to="{ name: 'home' }">
                <button id="library-button" class="hvr-bounce-in"><b-icon icon="house-door"></b-icon> Return Home</button>
            </router-link>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Library',

    data() {
        return {
            familyMember: this.$store.state.userDetails.username,
            listBooks: ''
        }
    },

    computed: {
        bookList() {
            
            let filteredBookList= [];
            let anotherArray= [];
            this.$store.state.familyMembers.forEach( familyMember => {
                if(this.familyMember == familyMember.username) {
                    let bookList = familyMember.books;

                    for (let book of bookList) {
                      
                      if (!filteredBookList.includes(book.title)){
                        anotherArray.push(book)
                        filteredBookList.push(book.title)
                        
                      }
                    }
                } 
            })
    
            return anotherArray;
        }
//         bookList() {
//             let bookList = '';
//             let bookTitles = [];
//             let singleBookTitles = [];
//             this.$store.state.familyMembers.forEach(member => {
//                 if (this.familyMember == member.username) {
//                     bookList = member.books
//                     bookTitles.push(member.books.title)

//                 }
//             })
//             bookList.forEach(book => {
//                 if(!bookTitles.includes(book.title)) {
//                     singleBookTitles.push(book);
//                 }

//             })
//             return singleBookTitles;
// }
    }
}
</script>

<style>

#libraryheader {
  width: 80%;
}

#star {
    width: 20px;
}

#library-button {
    width: 250px;
}

.navigation {
    display: flex;
    flex-direction: row;
}

.library-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    width: 80%;
}

.library-table {
    background: white;
    text-align: center;
    color: #545454;
    border-radius: 10px;
    padding: 10px;
    width: 80%;
}

tr {
    padding: 5px;
}

</style>