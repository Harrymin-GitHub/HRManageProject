<template>
  <div>
    <svg-icon icon-class="question" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'HarryMinDoc',
  data() {
    return {
      url: 'https://github.com/Harrymin-GitHub/HRManageProject.git'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
