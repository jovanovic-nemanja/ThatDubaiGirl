<template>
    <div class="ps-shopping-product">
        <div class="ps-product ps-product--wide" v-for="prod in products" :key="prod.slug">
            <div class="ps-product__thumbnail">
                <a :href="`product/${prod.slug}`">
                    <img :src="`uploads/${prod.url}`" alt="">
                </a>
            </div>
            <div class="ps-product__container">
                <div class="ps-product__content">
                    <a class="ps-product__title" :href="`product/${prod.slug}`">{{ prod.name.substr(0, 20) }}</a>
                    <p class="text-muted" v-html="prod.description"></p>
                    <p class="ps-product__vendor">Company: <a :href="`purchaseorders/userreview/${prod.user_id}`" style="color: blue;">{{ prod.company_name }}</a></p>
                </div>
                <div class="ps-product__shopping">
                    <p class="ps-product__price">{{ prod.price_from }} ~ {{ prod.price_to }} {{ localization_setting.currency }}</p>
                    <a v-if="role == 'buyer'" class="ps-btn" :href="`request/create/${prod.id}`">Request</a>
                    <a v-if="role == 'guest'" class="ps-btn" :href="`request/create/${prod.id}`">Request</a>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
    
</style>

<script>
    export default{
        data(){
            return {
                
            }
        },

        props: ['products'],

        computed: {
            localization_setting(){
                return this.$store.getters.LOCALIZATION_SETTINGS;
            },
            role(){
                return this.$store.getters.ROLE;
            }
        },

        methods: {   
        }
    }
</script>