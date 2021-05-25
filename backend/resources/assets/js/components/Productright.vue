<template>
    <div class="ps-shopping ps-tab-root">
        <div class="ps-shopping__header">
            <p><strong v-if="products.length != 0"> {{products.data.data.length}} </strong> Products found</p>
            <div class="ps-shopping__actions">
                <select name="sort" class="form-control" style="width: 200px !important; height: 40px;" @change="onchange()" v-model="key">
                    <option value="">Choose sort</option>
                    <option value="1">Latest to Old</option>
                    <option value="2">Old to Latest</option>
                    <option value="3">Low to High</option>
                    <option value="4">High to Low</option>
                </select>
                <div class="ps-shopping__view">
                    <p>View</p>
                    <ul class="ps-tab-list">
                        <li class="active"><a href="#tab-1"><i class="icon-grid"></i></a></li>
                        <li><a href="#tab-2"><i class="icon-list4"></i></a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="ps-tabs">
            <loading :active.sync="isLoading" 
            :can-cancel="true" 
            :on-cancel="onCancel"
            :is-full-page="fullPage"></loading>
            <div class="ps-tab active" id="tab-1">
                <product-lists v-if="products.length != 0" :products="products.data.data"></product-lists>
                
                <div class="ps-pagination">
                    <div class='row' style="display: block;">
                        <pagination v-if="products.length != 0" :data="products.data" @pagination-change-page="getResults"></pagination>
                    </div>
                </div>
            </div>
            <div class="ps-tab" id="tab-2">
                <product-thumbnail-lists v-if="products.length != 0" :products="products.data.data"></product-thumbnail-lists>
                <div class="ps-pagination">
                    <div class='row' style="display: block;">
                        <pagination v-if="products.length != 0" :data="products.data" @pagination-change-page="getResults"></pagination>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>

</style>

<script>
    import Loading from 'vue-loading-overlay';
    import 'vue-loading-overlay/dist/vue-loading.css';
    
    export default{
        data(){
            return {
                isLoading: false,
                fullPage: true,
                key: ''
            }
        },

        computed: {
            products(){
                this.isLoading = true;
                setTimeout(() => {
                    this.isLoading = false;
                }, 1000);

                return this.$store.getters.PRODUCTS_BY_CATEGORY;
            }
        },

        components: {
            Loading
        },
        
        methods: {
            getResults(page) {
                if (typeof page === 'undefined') {
                    page = 1;
                }
      
                const body ={
                    word: this.$store.getters.WORD,
                    by: this.$store.getters.BY,
                    min_price: this.$store.getters.MIN_PRICE,
                    max_price: this.$store.getters.MAX_PRICE,
                    category: this.$store.getters.CATEGORY,
                    sort: this.$store.getters.SORT,
                    page: page
                }

                this.$store.dispatch('GET_PRODUCTS', body);
            },

            onchange: function() {
                console.log(this.key)
     
                const body ={
                    word: this.$store.getters.WORD,
                    by: this.$store.getters.BY,
                    min_price: this.$store.getters.MIN_PRICE,
                    max_price: this.$store.getters.MAX_PRICE,
                    category: this.$store.getters.CATEGORY,
                    sort: this.key,
                    page: this.$store.getters.PAGE
                }

                this.$store.dispatch('GET_PRODUCTS', body);
            },

            onCancel() {
              console.log('User cancelled the loader.')
            } 
        },

        created: async function() {            
            var queryString = window.location.search;
            const urlParams = new URLSearchParams(queryString);
            const category = urlParams.get('category');
            const sort = urlParams.get('sort');
            const word = urlParams.get('word');

            if(category == '') {
                var value = -1;
            }else{
                var value = category;
            }

            if(word == '') {
                var w_val = '';
            }else{
                var w_val = word;
            }

            if(sort == '') {
                var sort_val = -1;
            }else{
                var sort_val = sort;
            }

            const body ={
                word: w_val,
                by: '',
                min_price: 0,
                max_price: 1000000,
                category: value,
                sort: sort_val,
                page: 1
            }

            const res1 = await this.$store.dispatch('GET_PRODUCTS', body);
        }
    }
</script>