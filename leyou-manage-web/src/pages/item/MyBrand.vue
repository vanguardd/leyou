<template>
    <div>
        <v-card>
            <v-card-title>
                <v-btn color="info">新增品牌</v-btn>
                <v-spacer />
                <v-text-field 
                    append-icon="search"
                    label="搜索"
                    v-model="search"
                    hide-details
                />                
            </v-card-title>
            <v-divider/>
            <v-data-table
            :headers="headers"
            :items="brands"
            :pagination.sync="pagination"
            :server-items-length="totalBrands"
            :loading="loading"
            class="elevation-1"
            >
                <template slot="items" slot-scope="props">
                    <td class="text-xs-center">{{ props.item.id }}</td>
                    <td class="text-xs-center">{{ props.item.name }}</td>
                    <td class="text-xs-center"><img :src="props.item.image" /></td>
                    <td class="text-xs-center">{{ props.item.letter }}</td>
                    <td class="text-xs-center">
                        <v-btn flat icon color="info">
                            <v-icon>edit</v-icon>
                        </v-btn>
                        <v-btn flat icon color="red">
                            <v-icon>delete</v-icon>
                        </v-btn>
                    </td>
                </template>
            </v-data-table>
        </v-card>
    </div>
</template>

<script>
export default {
    name: "my-brand",
     data () {
       return {
        totalBrands: 0,
        brands: [],
        loading: false,
        pagination: {},
        headers: [
          { text: '品牌id', align: 'center', sortable: true, value: 'id' },
          { text: '品牌名称', value: 'name', sortable: false, align: 'center' },
          { text: '品牌LOG', value: 'image', sortable: false, align: 'center' },
          { text: '品牌首字母', value: 'letter', align: 'center', sortable: true },
          { text: '操作', align: 'center', sortable: false }
        ],
        search: ''
      }
    },
    created () {
        this.loading = true;
        this.brands = [
                {
                    "id": 2032,
                    "name": "OPPO",
                    "image": "http://img10.360buyimg.com/popshop/jfs/t2119/133/2264148064/4303/b8ab3755/56b2f385N8e4eb051.jpg",
                    "letter": "O",
                }, {"id": 2505, "name": "TCL", "image": "", "letter": "T"}, {
                    "id": 3177,
                    "name": "爱贝多",
                    "image": "",
                    "letter": "A",
                }, {"id": 3539, "name": "安桥（ONKYO）", "image": "", "letter": "A"}, {
                    "id": 3941,
                    "name": "白金（PLATINUM）",
                    "image": "",
                    "letter": "B",
                }, {"id": 4986, "name": "波导（BiRD）", "image": "", "letter": "B"}, {
                    "id": 6522,
                    "name": "朵唯（DOOV）",
                    "image": "",
                    "letter": "D",
                }
        ]
        this.totalBrands = 15;
        this.loading = false;

        this.loadBrands();
    },
    watch: {
        search () {
            this.loadBrands();
        },
        pagination: {
            handler() {
                this.loadBrands();
            },
            deep: true
        }
    },
    methods: {
        loadBrands() {
            this.loading = true;
            this.$http.get("/item/brand/page", {
                params: {
                    page: this.pagination.page, //当前页
                    rows:　this.pagination.rowsPerPage, //每页大小
                    sortBy: this.pagination.sortBy, //排序字段
                    desc: this.pagination.descending, // 是否降序
                    key: this.search //搜索关键字
                }
            }).then(res => {
                this.brands = res.data.items
                this.totalBrands = res.data.total
                this.loading = false
            })       
        }
    }
}
</script>

<style scoped>

</style>