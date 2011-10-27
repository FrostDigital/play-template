#{ifErrors}
	<div class="alert-message error">
		<p>Oops, we can't submit the form due to some error(s)</p>
	</div>
#{/ifErrors}

#{if flash.error}
	<div class="alert-message error">
		<p>&{flash.error}</p>
	</div>
#{/if}
#{if flash.success}
	<div class="alert-message info">
		<p>&{flash.success}</p>
	</div>
#{/if}
				