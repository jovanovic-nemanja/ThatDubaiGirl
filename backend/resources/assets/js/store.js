import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    categoris: [],
    role: '',
    products_by_category: [],
    localization_setting: [],
    word: '',
    by: '',
    min_price: 0,
    max_price: 1000000,
    category: '',
    sort: '',
    page: 1,
  },

  getters: {    //getter
    CATEGORIS: state => state.categoris,
    ROLE: state => state.role,
    PRODUCTS_BY_CATEGORY: state => state.products_by_category,
    LOCALIZATION_SETTINGS: state => state.localization_setting,
    WORD: state => state.word,
    BY: state => state.by,
    MIN_PRICE: state => state.min_price,
    MAX_PRICE: state => state.max_price,
    CATEGORY: state => state.category,
    SORT: state => state.sort,
    PAGE: state => state.page,
  },
  
  mutations: {  //setter
    SET_CATEGORIS: (state, payload) => {
      state.categoris = payload;
    },

    SET_ROLE: (state, payload) => {
        state.role = payload;
    },

    SET_PRODUCTS_BY_CATEGORY: (state, payload) => {
        state.products_by_category = payload;
    },

    SET_LOCALIZATION_SETTINGS: (state, payload) => {
        state.localization_setting = payload;
    },

    SET_WORD: (state, payload) => {
      state.word = payload;
    },
    SET_BY: (state, payload) => {
      state.by = payload;
    },
    SET_MIN_PRICE: (state, payload) => {
      state.min_price = payload;
    },
    SET_MAX_PRICE: (state, payload) => {
      state.max_price = payload;
    },
    SET_CATEGORY: (state, payload) => {
      state.category = payload;
    },
    SET_SORT: (state, payload) => {
      state.sort = payload;
    },
    SET_PAGE: (state, payload) => {
      state.page = payload;
    },
  },

  actions: {
    GET_CATEGORIS: async (context) => {
        const response = await window.axios.get('/api-getcategory');
        context.commit('SET_CATEGORIS', response.data.categorys);
        return response.status === 200;
    },

    GET_PRODUCTS : async (context, payload) => {
        const word = (payload.word == '') ? null : payload.word;
        const by = (payload.by == '') ? null : payload.by;
        const min_price = (payload.min_price == 0) ? null : payload.min_price;
        const max_price = (payload.max_price == '') ? null : payload.max_price;
        const category = (payload.category == -1) ? null : payload.category;
        const sort = (payload.sort == -1) ? null : payload.sort;

        const url = `/getproducts-byfilter/${word}/${by}/${min_price}/${max_price}/${category}/${sort}?page=${payload.page}`;
        const response = await window.axios.get(url);

        context.commit('SET_PAGE', payload.page);
        context.commit('SET_CATEGORY', category);
        context.commit('SET_SORT', sort);
        context.commit('SET_MAX_PRICE', max_price);
        context.commit('SET_MIN_PRICE', min_price);
        context.commit('SET_BY', by);
        context.commit('SET_WORD', word);

        context.commit('SET_PRODUCTS_BY_CATEGORY', response);
    },

    GET_LOCALIZATION_SETTINGS: async (context) => {
        const response = await window.axios.get('/api-getcurrency');
        context.commit('SET_LOCALIZATION_SETTINGS', response.data);
        return response.status;
    },

    GET_ROLE: async (context) => {
      const response = await window.axios.get('/api-getrole');
      context.commit('SET_ROLE', response.data);
      return response.status;
    }
  }
});
