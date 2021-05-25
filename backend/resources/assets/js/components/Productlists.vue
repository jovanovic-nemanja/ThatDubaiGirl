<template>
    <div class="ps-shopping-product">
        <div class="row">
            <div class="col-xl-4 col-lg-4 col-md-4 col-sm-6 col-6" v-for="prod in products" :key="prod.slug">
                <productlist :prod="prod"></productlist>
            </div>
        </div>
    </div>
</template>

<style scoped>
    
</style>

<script>
    import productlist from './Productlist.vue';

    export default{
        components: {
            productlist
        },

        props: ['products'],

        data(){
            return {
            }
        },

        computed: {
        },

        methods: {
        }
    }
</script>