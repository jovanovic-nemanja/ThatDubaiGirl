<template>
    <div class="ps" style="padding: 5%; margin-bottom: 4%; border-radius: 3px;">
        <div class="ps-product__thumbnail">
            <a :href="`product/${prod.slug}`">
                <img :src="`uploads/${prod.url}`" alt="" style="width: 100%; height: 145px; object-fit: cover;">
            </a>
        </div>
        <div class="ps-product__container">
            <a class="ps-product__title" :href="`product/${prod.slug}`">{{ prod.name.substr(0, 20) }}</a>
            <hr>
            <div class="ps-product__content">
                Company: <a class="ps-product__vendor" :href="`purchaseorders/userreview/${prod.user_id}`" style="color: blue;">{{ prod.company_name }}</a>
                <p class="ps-product__price">{{ prod.price_from }} ~ {{ prod.price_to }} {{ localization_setting.currency }}</p>
                <p class="ps-product__price">{{ prod.MOQ }} piece (Min order)</p>
            </div>

            <input type="hidden" class="hidden_product_name" :value="`${prod.name}`">

            <div style="text-align: center;">
                <a v-if="role == 'buyer'" class="ps-btn rfq" :href="`request/create/${prod.id}`">Request</a>
                <a v-if="role == 'guest'" class="ps-btn rfq" :href="`request/create/${prod.id}`">Request</a>
            </div>
        </div>
    </div>
</template>

<style scoped>
    
</style>

<script>
    export default{
        props: ['prod'],

        data(){
            return {
                url: '',
            }
        },

        computed: {
            localization_setting(){
                return this.$store.getters.LOCALIZATION_SETTINGS;
            },
            role(){
                return this.$store.getters.ROLE;
            }
        },

        methods: {
            
        },

        created: async function() {
            const res = await this.$store.dispatch('GET_LOCALIZATION_SETTINGS');
            const res1 = await this.$store.dispatch('GET_ROLE');
        }
    }
</script>