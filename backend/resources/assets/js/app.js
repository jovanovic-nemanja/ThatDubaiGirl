
/**
 * First we will load all of this project's JavaScript dependencies which
 * includes Vue and other libraries. It is a great starting point when
 * building robust, powerful web applications using Vue and Laravel.
 */

require('./bootstrap');

window.Vue = require('vue');

window.events = new Vue();

/**
 * Next, we will create a fresh Vue application instance and attach it to
 * the page. Then, you may begin adding components to this application
 * or customize the JavaScript scaffolding to fit your unique needs.
 */

Vue.component('flash-message', require('./components/Flash.vue'));
Vue.component('file-upload', require('./components/FileUpload.vue'));
Vue.component('file-upload-multiple', require('./components/FileUploadMultiple.vue'));
Vue.component('address-picker', require('./components/AddressPicker.vue'));
Vue.component('home-header', require('./components/Homeheader.vue'));
Vue.component('product-category-left', require('./components/Productleftcategory.vue'));
Vue.component('product-right', require('./components/Productright.vue'));
Vue.component('product-lists', require('./components/Productlists.vue'));
Vue.component('product-thumbnail-lists', require('./components/Productthumbnaillists.vue'));
Vue.use(require('vue-resource'));
 
Vue.component('pagination', require('laravel-vue-pagination'));

import store from './store';

const app = new Vue({
    el: '#app',
    store
});

$('.carousel').carousel({
    interval: false
});