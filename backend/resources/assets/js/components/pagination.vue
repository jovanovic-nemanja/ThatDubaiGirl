<script>
    export default {
        props: ['products'],

        data() {
            return {
                
            }
        },

        computed: {
            
        },
        methods: {
            
        }
    };
</script>
<template>
    <ul class="pagination" role="navigation">
        <li class="disabled" aria-disabled="true" aria-label="@lang('pagination.previous')" v-if="products.current_page <= 1">
            <a style="cursor: not-allowed;"><i class="icon-chevron-left"></i>Prev Page</a>
        </li>
        <li v-else>
            <a rel="prev" aria-label="@lang('pagination.previous')"><i class="icon-chevron-left"></i>Prev Page</a>
        </li>
    </ul>
</template>