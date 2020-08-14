import axios from 'axios';

export default {
    getFamilyMembers(familyName) {
        return axios.get(`/family-info/${familyName}`)
    },

    addBook(book) {
        return axios.post('/add-book', book);
    },

    
    updateLog(log) {
        return axios.post('/log', log);
    },

    addPrize(prize) {
        return axios.post('/add-prize', prize)
    },

    getPrizes(familyName) {
        return axios.get(`/get-prizes/${familyName}`)
    },

    getPrizeInfo(familyName) {
        return axios.get(`/list-awards/${familyName}`)
    },

    awardPrize(familyName, prize) {
      return axios.post(`/award-user/${familyName}`, prize)
    },

    getMostRecentLogs(familyName) {
        return axios.get(`/show-logs/${familyName}`)
    },


    updatePrize(prize) {
        return axios.put('/update-prize', prize)
    },

    deletePrize(id) {
        return axios.delete(`/delete-prize/${id}`)
    }

}