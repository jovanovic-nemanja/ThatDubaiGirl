<template>
    <div>
        <aside class="widget widget_shop">
            <h4 class="widget-title">Categories</h4>
            <ul class="ps-list--categories">
                <li class="current-menu-item menu-item-has-children" v-for="cate in categories" :key="cate.id">
                    <a @click="showListproduct(cate.slug)" style="cursor: pointer;" :class="{'active': cate.slug == category}">{{ cate.name }}</a>
                    <!-- <span class="sub-toggle" v-if="cate.childs.length"><i class="fa fa-angle-down"></i></span>
                    <ul class="sub-menu" v-for="ct in cate.childs" :key="ct.id">
                        <li class="current-menu-item">
                            <a @click="showListproduct(ct.slug)" style="cursor: pointer;">{{ ct.name }}</a>
                        </li>
                    </ul> -->
                </li>
            </ul>
        </aside>
        
        <aside class="widget widget_shop">
            <form class="ps-form--widget-search" style="padding: 3%;">
                <div class="row">
                    <h4 class="widget-title">BY PRODUCT</h4>
                    <input class="form-control" type="text" placeholder="" name="word" v-model="word">
                </div>
                <br>
                <div class="row">
                    <br>
                    <h4 class="widget-title">BY SELLER</h4>
                    <input class="form-control" type="text" placeholder="" name="by" v-model="by">
                </div>
                <figure>
                    <h4 class="widget-title">By Price</h4>
                    <div id="nonlinear"></div>
                    <p class="ps-slider__meta"><span class="ps-slider__value">{{ localization_setting.currency }}<span class="ps-slider__min"></span></span>-<span class="ps-slider__value">{{ localization_setting.currency }}<span class="ps-slider__max"></span></span></p>
                </figure>

                <div class="row submit" style="display: block; text-align: center;">
                    <button class="ps-btn" @click="submit" style="position: initial; transform: translateY(20%);">Search</button>
                </div>
            </form>
        </aside>
    </div>
</template>

<style scoped>
    .showpointer {
        cursor: pointer;
    }
    .fa {
        font-size: 15px;
    }
    .active {
        color: red;
        font-weight: bold;
    }
</style>

<script>
    export default{
        data(){
            return {
                min_price: 0,
                category: '',
                sort: '',
                max_price: 1000000,
                by: '',
                word: ''
            }
        },

        computed: {
            categories(){
                return this.$store.getters.CATEGORIS;
            }, 
            localization_setting(){
                return this.$store.getters.LOCALIZATION_SETTINGS;
            }
        },
        
        methods: {
            async showListproduct(slug) {
                const body ={
                    word: this.word,
                    by: this.by,
                    min_price: this.min_price,
                    max_price: this.max_price,
                    category: slug,
                    sort: this.sort,
                    page: 1
                }

                this.category = slug;
                const res1 = await this.$store.dispatch('GET_PRODUCTS', body);
            },

            submit(e) {
                e.preventDefault();
                const min = document.getElementsByClassName('ps-slider__min')[0].innerText;
                const max = document.getElementsByClassName('ps-slider__max')[0].innerText;
                const cate_val = (this.category == '') ? -1 : this.category;
                const body ={
                    word: this.word,
                    by: this.by,
                    min_price: min,
                    max_price: max,
                    category: cate_val,
                    sort: this.sort,
                    page: 1
                }

                const res = this.$store.dispatch('GET_PRODUCTS', body);
            }
        },

        created: async function() {
            var queryString = window.location.search;
            const urlParams = new URLSearchParams(queryString);
            const cate = urlParams.get('category');
            const sort = urlParams.get('sort');
            this.category = cate;
            this.sort = sort;

            const res = await this.$store.dispatch('GET_CATEGORIS');
            const res1 = await this.$store.dispatch('GET_LOCALIZATION_SETTINGS');
        }
    }
</script>